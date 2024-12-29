package com.novaengine.easyroutes

import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.JsonArray
import com.novaengine.easyroutes.databinding.ActivityBookInfoBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService

class BookInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding
        binding = ActivityBookInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get info from bundle
        val bundle = intent?.extras
        val bookId = bundle?.getString("bookId")
        val destinationName = bundle?.getString("destinationName")
        var idStruttura: String? = null

        // Set info to context
        binding.bookId.text = bookId.toString()
        binding.destinationName.text = destinationName

        if (bookId != null) {
            DBQueriesService().getBookInfo(bookId) { result ->
                if (result != null && result.has("queryset")) {
                    val jsonArray = result.get("queryset") as JsonArray
                    val jsonObject = jsonArray.get(0).asJsonObject
                    var inizio = jsonObject.get("Data_andata").asString
                    var fine = jsonObject.get("Data_ritorno").asString
                    var Carta = jsonObject.get("Carta").asString
                    idStruttura = jsonObject.get("Id_struttura").asString
                    binding.range2.text = "${resources.getString(R.string.user_book_info_from_date)} ${inizio} ${resources.getString(R.string.user_book_info_to_date)} ${fine}."
                    binding.cartausata.text = "${resources.getString(R.string.user_book_info_credit_card)}: " + Carta.subSequence( 12, 16)
                    binding.numeropersone.text = "${resources.getString(R.string.user_book_info_people_count)}: " + jsonObject.get("Persone").asString
                    binding.costocomplessivo.text = "${resources.getString(R.string.user_book_info_total_price)}: " + jsonObject.get("Pagamento").asString + " €"
                    binding.mezzo.text = "${resources.getString(R.string.user_book_info_vehicle)}: " + jsonObject.get("Mezzo").asString
                    var Id_Camera = jsonObject.get("Id_camera").asString
                    Log.i("test",Id_Camera)

                    DBQueriesService().getRoomInfo(Id_Camera) { userInfo ->
                        if (userInfo != null) {
                            val jsonArray = userInfo.get("queryset") as JsonArray
                            val data = jsonArray.get(0).asJsonObject
                            var Tipologia = data.get("Tipologia").asString
                            binding.tipologia.text = "${resources.getString(R.string.user_book_info_room_type)}: " + Tipologia

                            DBQueriesService().getProfileImage(data.get("Immagine").asString) { userProfileImage ->
                                if (userProfileImage != null) {
                                    val drawable = BitmapDrawable(resources, userProfileImage)
                                    binding.imageView.setImageDrawable(drawable)
                                }
                            }
                        }
                    }
                }
                else {
                    Log.e("BooksFragment", "An error occured while getting user books...")
                }
            }
        }

        binding.deleteButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.user_book_delete_title))
                .setMessage(resources.getString(R.string.user_book_delete_message) + " " + bookId.toString() + "?")
                .setPositiveButton(resources.getString(R.string.user_book_delete_message_confirm)) { dialog, which ->

                    DBQueriesService().deleteUserBook(bookId.toString()) { okay ->
                        if (okay) {
                            Log.i("BookInfoActivity", "User book has been delete.")
                            Toast.makeText(this, resources.getString(R.string.user_book_delete_success), Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Log.e("BookInfoActivity", "An error occured while deleting this user book.")
                            Toast.makeText(this, resources.getString(R.string.user_book_delete_fail), Toast.LENGTH_SHORT).show()
                        }
                    }
                    finish()
                }
                .setNegativeButton("No") { dialog, which ->
                    Log.i("BookInfoActivity", "Action canceled.")
                }

            val alertDialog = builder.create()
            alertDialog.show()
        }

        binding.rateButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.user_book_rating_selection_title))

            val spinner = Spinner(this)
            val options = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            builder.setView(spinner)

            builder.setPositiveButton("OK") { dialog, which ->
                val selectedItem = spinner.selectedItem as String

                DBQueriesService().insertUserRating(idStruttura.toString(), selectedItem.toString()) { okay ->
                    if(okay) {
                        Toast.makeText(this, resources.getString(R.string.user_book_rating_inserted_confirm), Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, resources.getString(R.string.user_book_rating_inserted_error), Toast.LENGTH_SHORT).show()
                    }
                }


            }

            builder.setNegativeButton(resources.getString(R.string.user_book_rating_cancel)) { dialog, which ->
                dialog.cancel()
            }

            val dialog = builder.create()
            dialog.show()

        }

        binding.specialRequestButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.user_book_special_request_title))

            val input = EditText(this)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            input.layoutParams = lp
            builder.setView(input)

            builder.setPositiveButton("OK") { dialog, which ->
                Toast.makeText(this, resources.getString(R.string.user_book_special_request_confirm), Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            builder.setNegativeButton(resources.getString(R.string.button_cancel)) { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }

    }
}