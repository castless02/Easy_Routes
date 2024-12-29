package com.novaengine.easyroutes.serversupport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.novaengine.easyroutes.InfoInterface
import com.novaengine.easyroutes.serversupport.dataclasses.LoginData
import com.novaengine.easyroutes.serversupport.dataclasses.PrenotazioneData
import com.novaengine.easyroutes.serversupport.dataclasses.RegisterData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DBQueriesService {

    // Function to get connection status
    fun getServerConnectionStatus(callback: (Boolean) -> Unit) {
        val query = "select * from Utente where Id = '0';"
        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    callback(true)
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(false)
                }
            }
        )
    }

    // Function to get user information
    fun getUserInformation(id: String, callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "select * from Utente where Id = '${id}';"
        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val userInfo = response.body()
                        callback(userInfo)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }
    //fatta
    // Function to get user information
    fun getDestinationInfo(id: String, callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "select * from Struttura where Id_struttura = '${id}';"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val userInfo = response.body()
                        callback(userInfo)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    fun getPaymentsInfo(id: String, callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "select * from Pagamento join U_P UP on Pagamento.Id_Pagamento = UP.ref_Id_pagamento where Id_Utente = '${id}' order by Id_Pagamento desc limit 1;"
        Log.v("info", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("info", "Response is successful...")
                        val userInfo = response.body()
                        callback(userInfo)
                    } else {
                        Log.e("info", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    // Function to get user profile image
    fun getProfileImage(url: String, callback: (Bitmap?) -> Unit) {

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.getAvatar(url).enqueue(
            object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val avatar = BitmapFactory.decodeStream(response.body()?.byteStream())
                        callback(avatar)
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )

    }

    // Function to get images
    fun getMeteImage(url: String, callback: (Bitmap?) -> Unit) {

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.getAvatar(url).enqueue(
            object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val image = BitmapFactory.decodeStream(response.body()?.byteStream())
                        callback(image)
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )

    }

    // Login function
    fun login(loginData: LoginData, callback: (String?) -> Unit) {

        // Selection query
        Log.v("DBQueriesService", "Requesting login ID from database...")
        val query = "select Id from Utente where Email = '${loginData.email}' and Password = '${loginData.password}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val userInfo = response.body()
                        val jsonArray = userInfo?.get("queryset") as JsonArray

                        // Check if the user exists
                        Log.v("DBQueriesService", "Checking if user ID exists...")
                        if (jsonArray != null && jsonArray.size() != 0) {
                            // This means the user exists, so its id can be returned
                            Log.v("DBQueriesService", "OKAY! The user ID exists, so it can be returned as callback...")
                            val jsonObject = jsonArray.get(0).asJsonObject
                            val userId = jsonObject.get("Id").asString
                            callback(userId)
                        }
                        else {
                            // The user ID doesn't exist, so it can't be returned as callback
                            Log.v("DBQueriesService", "ERROR! The user ID doesn't exist, so it can't be returned as callback...")
                            callback(null)
                        }
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )

    }

    fun Checkcoupon(Coupon:String, callback: (String?) -> Unit) {

        // Selection query
        Log.v("DBQueriesService", "Requesting login ID from database...")
        val query = "select Credito from Coupon where Codice = '${Coupon}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val userInfo = response.body()
                        val jsonArray = userInfo?.get("queryset") as JsonArray

                        // Check if the user exists
                        Log.v("DBQueriesService", "Checking if user ID exists...")
                        if (jsonArray != null && jsonArray.size() != 0) {
                            // This means the user exists, so its id can be returned
                            Log.v("DBQueriesService", "OKAY! The user ID exists, so it can be returned as callback...")
                            val jsonObject = jsonArray.get(0).asJsonObject
                            val credito = jsonObject.get("Credito").asString
                            callback(credito)
                        }
                        else {
                            // The user ID doesn't exist, so it can't be returned as callback
                            Log.v("DBQueriesService", "ERROR! The user ID doesn't exist, so it can't be returned as callback...")
                            callback(null)
                        }
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )

    }

    fun changeCredito(ID:String,Credito:String) {

        val query = "UPDATE Utente SET Coupon = '${Credito}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        val responseBody = response.body()?.get("queryset")
                        if(responseBody is JsonArray && responseBody.size() == 1) {
                            Log.i("DBQueriesService", "Success")
                        }
                        else {
                            Log.e("DBQueriesService", "Failed")
                        }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )
    }

    fun changeAvatar(ID:String,Foto:String) {

        val query = "UPDATE Utente SET Foto_profilo = '${Foto}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()?.get("queryset")
                        if(responseBody is JsonArray && responseBody.size() == 1) {
                            Log.i("DBQueriesService", "Success")
                        }
                        else {
                            Log.e("DBQueriesService", "Failed")
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )
    }

    fun register(registerData: RegisterData, callback: (Boolean) -> Unit) {

        // Insertion query
        val query = "insert into Utente (Nome, Cognome, Email, Password, Sesso, Telefono, Residenza, Coupon, Foto_profilo, Verifica_account, Richieste_speciali) values ('${registerData.name}', '${registerData.surname}', '${registerData.email}', '${registerData.password}', 'N/A', 'N/A', 'N/A', '0', 'media/images/icone/icon1.png', '0', 'N/A');"

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                        Log.i("DBQueriesService", "Registration successful!")
                        callback(true)
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.i("DBQueriesService", "Registration failed!")
                    Log.e("DBQueriesService", t.message.toString())
                    callback(false)
                }
            }
        )

    }
    //Fatto
    fun salvacarta( Intestatario:String, Carta:String, CVV:String, Scandenza:String,Id:String, callback: (Boolean) -> Unit) {

        // Insertion query
        val query = "insert into Pagamento (Intestatario, Num_carta, CVV, Data_scadenza) values ('${Intestatario}', '${Carta}', '${CVV}', '${Scandenza}' );"
        Log.i("info" , Intestatario + Carta + Scandenza + CVV + Id)
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
                {
                    val query="select Id_Pagamento from Pagamento order by Id_Pagamento desc limit 1;"
                    ClientNetworkService.retrofit.provideQuery(query).enqueue(
                        object: Callback <JsonObject> {
                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
                            {
                                val data=response.body()
                                if (data!=null)
                                {
                                    val jsonArray=data.get("queryset") as JsonArray
                                    val lastInsertedId = jsonArray.get(0).asJsonObject.get("Id_Pagamento").asString
                                    val query="insert into U_P (Id_Utente, ref_Id_pagamento) values ('${Id}', '${lastInsertedId}')"
                                    ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                        object: Callback <JsonObject> {
                                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
                                            {
                                                callback(true)
                                            }
                                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                                Log.i("info", "Registration failed!")
                                                Log.e("info", t.message.toString())
                                                callback(false)
                                            }
                                        }
                                    )
                                }
                            }
                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                Log.i("info", "Registration failed!")
                                Log.e("info", t.message.toString())
                                callback(false)
                            }
                        }
                    )
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.i("info", "Registration failed!")
                    Log.e("info", t.message.toString())
                    callback(false)
                }
            }
        )

    }
    fun prenota(prenotazioneData: PrenotazioneData,callback: (Boolean) -> Unit) {

        // Insertion query
        val query = "insert into Prenotazione_Viaggio (Data_Prenotazione, Eta, Data_andata, Data_ritorno, Pagamento, Persone, Carta, Mezzo) values ('${prenotazioneData.dataprenotazione}', '${prenotazioneData.eta}', '${prenotazioneData.dataandata}', '${prenotazioneData.dataritorno}', '${prenotazioneData.pagamento}', '${prenotazioneData.persone}', '${prenotazioneData.carta}', '${prenotazioneData.mezzo}')"
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
                {
                    val query = "select Id_Viaggio from Prenotazione_Viaggio order by Id_Viaggio desc limit 1;"
                    ClientNetworkService.retrofit.provideQuery(query).enqueue(
                        object: Callback <JsonObject> {
                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
                            {
                                val data=response.body()
                                if (data!=null)
                                {
                                    val jsonArray=data.get("queryset") as JsonArray
                                    val lastInsertedId = jsonArray.get(0).asJsonObject.get("Id_Viaggio").asString
                                    val query="insert into Effettua (Id_Utente, ref_Id_Viaggio, Id_camera) values ('${prenotazioneData.idutente}', '${lastInsertedId}', '${prenotazioneData.idcamera}');"
                                    ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                        object: Callback <JsonObject> {
                                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
                                            {
                                                val query = "insert into V_S (Ref_id_Viaggio, Ref_id_Struttura) values ('${lastInsertedId}', '${prenotazioneData.idstruttura}');"
                                                ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                                    object: Callback <JsonObject> {
                                                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
                                                        {
                                                            callback(true)
                                                        }
                                                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                                            Log.i("info", "Registration failed!")
                                                            Log.e("info", t.message.toString())
                                                            callback(false)
                                                        }
                                                    }
                                                )
                                            }
                                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                                Log.i("info", "Registration failed!")
                                                Log.e("info", t.message.toString())
                                                callback(false)
                                            }
                                        }
                                    )
                                }
                            }
                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                Log.i("info", "Registration failed!")
                                Log.e("info", t.message.toString())
                                callback(false)
                            }
                        }
                    )
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.i("DBQueriesService", "Registration failed!")
                    Log.e("DBQueriesService", t.message.toString())
                    callback(false)
                }
            }
        )

    }
    fun changePassword(ID:String, Password:String) {

        val query = "UPDATE Utente SET Password = '${Password}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        if((response.body()?.get("queryset") as JsonArray).size() == 1) {
                            Log.i("DBQueriesService", "Login successful!")

                        }
                        else {
                            Log.e("DBQueriesService", "Login failed!")
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    //   callback.onReturnValue("Errore 3")
                }
            }
        )
    }

    fun changeNome(ID:String,Nome:String) {

        val query = "UPDATE Utente SET Nome = '${Nome}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()?.get("queryset")
                        if(responseBody is JsonArray && responseBody.size() == 1) {
                            Log.i("DBQueriesService", "Success")
                        }
                        else {
                            Log.e("DBQueriesService", "Failed")
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )
    }

    fun changeCognome(ID:String,Cognome:String){

        val query = "UPDATE Utente SET Cognome = '${Cognome}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) { val responseBody = response.body()?.get("queryset")
                        if(responseBody is JsonArray && responseBody.size() == 1) {
                            Log.i("DBQueriesService", "Success")
                        }
                        else {
                            Log.e("DBQueriesService", "Failed")
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )
    }

    fun changeNumber(ID:String,Number:String) {

        val query = "UPDATE Utente SET Telefono = '${Number}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()?.get("queryset")
                        if(responseBody is JsonArray && responseBody.size() == 1) {
                            Log.i("DBQueriesService", "Success")
                        }
                        else {
                            Log.e("DBQueriesService", "Failed")
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )
    }

    fun changeResidenza(ID:String,Residenza:String) {

        val query = "UPDATE Utente SET Residenza = '${Residenza}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()?.get("queryset")
                        if(responseBody is JsonArray && responseBody.size() == 1) {
                            Log.i("DBQueriesService", "Success")
                        }
                        else {
                            Log.e("DBQueriesService", "Failed")
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )
    }

    fun changeSesso(ID:String,Sesso:String) {

        val query = "UPDATE Utente SET Sesso = '${Sesso}' WHERE Id = '${ID}';"

        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()?.get("queryset")
                        if(responseBody is JsonArray && responseBody.size() == 1) {
                            Log.i("DBQueriesService", "Success")
                        }
                        else {
                            Log.e("DBQueriesService", "Failed")
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                }
            }
        )
    }

    fun changeEmail(ID:String, Email:String, callback: InfoInterface) {

        val query = "UPDATE Utente SET Email = '${Email}' WHERE Id = '${ID}';"
        val query2 = "SELECT Email FROM Utente WHERE Email = '${Email}';"
        Log.v("DBQueriesService", "Getting response from server...")

        ClientNetworkService.retrofit.provideQuery(query2).enqueue(
            object: Callback <JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        if((response.body()?.get("queryset") as JsonArray).size() == 0) {
                            ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                object: Callback <JsonObject> {
                                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                        if(response.isSuccessful) {
                                            if((response.body()?.get("queryset") as JsonArray).size() == 0) {
                                              //  callback.onReturnValue((response.body()?.get("queryset") as JsonArray).get(0).asJsonObject)
                                                Log.i("DBQueriesService", "Login successful!")

                                            }
                                            else {
                                                Log.e("DBQueriesService", "Login failed!")
                                            }
                                        }
                                        else {
                                         //   Log.e("DBQueriesService", response.message().toString())
                                        }
                                    }
                                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                     //   Log.e("DBQueriesService", t.message.toString())
                                        //   callback.onReturnValue("Errore 3")
                                    }
                                }
                            )
                            Log.i("DBQueriesService", "Pane")
                            callback.onReturnValue("fatto")
                        }
                        else {
                            Log.i("DBQueriesService", "Scolapasta")
                            callback.onError(("ESISTE"))
                        }
                    }
                    else {
                        Log.e("DBQueriesService", response.message().toString())
                        Log.i("DBQueriesService", "Condiriso")
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    Log.i("DBQueriesService", "Raooul sei un falluito")
                    callback.onError(("ESISTE"))
                }
            }
        )
    }

    fun getMostRequestedDestinations(callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "SELECT DISTINCT Luogo, Immagine_luogo from Struttura where Stelle >= 4;"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val result = response.body()
                        callback(result)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    fun getCategories(callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "select distinct Categoria, Immagine_categoria from Struttura"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val result = response.body()
                        callback(result)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )

    }

    fun getSearchResults(query: String, callback: (JsonObject?) -> Unit) {

        // Print selected query
        Log.v("DBQueriesService", "Selected query is: ${query}.")

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val result = response.body()
                        callback(result)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    fun getRooms(Id_Struttura:String,callback: (JsonObject?) -> Unit) {


        val query = "SELECT * FROM Camera C, Struttura S, S_C A WHERE C.Id_camera= A.Ref_id_Camera AND A.Ref_id_Struttura=S.Id_struttura AND S.Id_struttura = '${Id_Struttura}';"
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.i("view", result.toString())
                        callback(result)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    fun getRoomInfo(Id_room:String,callback: (JsonObject?) -> Unit) {


        val query = "SELECT * FROM Camera WHERE Id_camera = '${Id_room}';"
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.i("view", result.toString())
                        callback(result)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    fun getBookInfo(Id_Prenotazione: String, callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "select Data_andata, Data_ritorno, Carta, Id_struttura, Persone, Pagamento, Mezzo, Id_camera from Prenotazione_Viaggio join Effettua E on Prenotazione_Viaggio.Id_Viaggio = E.ref_Id_Viaggio join V_S on Prenotazione_Viaggio.Id_Viaggio = V_S.Ref_id_Viaggio join Struttura S on V_S.Ref_id_Struttura = S.Id_struttura where Id_Viaggio = '${Id_Prenotazione}';"


        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val result = response.body()
                        callback(result)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    fun getUserBookings(id: String, callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "select * from Prenotazione_Viaggio join Effettua E on Prenotazione_Viaggio.Id_Viaggio = E.ref_Id_Viaggio join V_S on Prenotazione_Viaggio.Id_Viaggio = V_S.Ref_id_Viaggio join Struttura S on V_S.Ref_id_Struttura = S.Id_struttura where Id_Utente = '${id}';"
        Log.v("DBQueriesService", id);

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.i("DBQueriesService", "Response is successful...")
                        val result = response.body()
                        callback(result)
                    } else {
                        Log.e("DBQueriesService", response.message().toString())
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )
    }

    fun deleteUserBook(id: String, callback: (Boolean) -> Unit) {

        // Selection query
        val query = "delete from V_S where ref_Id_Viaggio = '${id}';"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val query = "delete from Effettua where ref_Id_Viaggio = '${id}'"
                    ClientNetworkService.retrofit.provideQuery(query).enqueue(
                        object: Callback<JsonObject> {
                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                val query = "delete from Prenotazione_Viaggio where Id_Viaggio = '${id}'"
                                ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                    object: Callback<JsonObject> {
                                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                            Log.i("DBQueriesService", "Response is successful...")
                                            callback(true)
                                        }
                                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                            Log.e("DBQueriesService", t.message.toString())
                                            callback(false)
                                        }
                                    }
                                )
                            }
                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                Log.e("DBQueriesService", t.message.toString())
                                callback(false)
                            }
                        }
                    )
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(false)
                }
            }
        )
    }

    fun deleteCoupon(Codice: String, callback: (Boolean) -> Unit) {

        // Selection query
        val query = "delete from Coupon where Codice = '${Codice}';"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.i("DBQueriesService", "Response is successful...")
                    callback(true)
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(false)
                }
            }
        )
    }

    fun deletePaymentsId(id: String, callback: (Boolean) -> Unit) {

        // Selection query
        val query = "select * from U_P where Id_utente = '${id}' limit 1;"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val data = response.body()
                    if (data != null) {
                        val jsonArray = data.get("queryset") as JsonArray
                        if(jsonArray.size() != 0) {
                            val jsonObject = jsonArray.get(0).asJsonObject
                            val paymentId = jsonObject.get("ref_Id_pagamento").asString
                            val query = "delete from U_P where ref_Id_pagamento = '${paymentId}';"
                            ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                object: Callback<JsonObject> {
                                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                        val query = "delete from Pagamento where Id_Pagamento = '${paymentId}'"
                                        ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                            object: Callback<JsonObject> {
                                                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                                    Log.i("DBQueriesService", "Response is successful...")
                                                    callback(true)
                                                }
                                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                                    Log.e("DBQueriesService", t.message.toString())
                                                    callback(false)
                                                }
                                            }
                                        )
                                    }
                                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                        Log.e("DBQueriesService", t.message.toString())
                                        callback(false)
                                    }
                                }
                            )
                        }
                        else {
                            callback(false)
                        }
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(false)
                }
            }
        )
    }

    // Functions to get notifications from Easy Routes server
    fun getBookingStatusNotification(id: String, callback: (JsonObject?) -> Unit) {

        // Selection query
        val query = "select Id_Viaggio, Id_utente, Data_andata from Prenotazione_Viaggio join U_P on Id_Utente where Id_utente = '${id}';"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.i("DBQueriesService", "Response is successful...")
                    val result = response.body()
                    callback(result)
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("DBQueriesService", t.message.toString())
                    callback(null)
                }
            }
        )

    }

    // Functions to get notifications from Easy Routes server
    fun insertUserRating(id: String, rating: String, callback: (Boolean) -> Unit) {

        // Selection query
        val query = "insert into Stelle (Valutazione) values (${rating});"

        Log.v("DBQueriesService", "Getting response from server...")
        ClientNetworkService.retrofit.provideQuery(query).enqueue(
            object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val query = "select Id_valutazione from Stelle order by Id_valutazione desc limit 1;"
                    ClientNetworkService.retrofit.provideQuery(query).enqueue(
                        object: Callback<JsonObject> {
                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                val data = response.body()
                                if (data != null) {
                                    val jsonArray = data.get("queryset") as JsonArray
                                    Log.v("DBQueriesService", jsonArray.toString())
                                    val lastInsertedId = jsonArray.get(0).asJsonObject.get("Id_valutazione").asString
                                    Log.v("DBQueriesService", lastInsertedId)
                                    val query = "insert into S_S (Id_valutazione, Id_struttura) values ('${lastInsertedId}', '${id}');"
                                    ClientNetworkService.retrofit.provideQuery(query).enqueue(
                                        object: Callback<JsonObject> {
                                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                                Log.v("DBQueriesService", "Miao")
                                                callback(true)
                                            }
                                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                                Log.v("DBQueriesService", "Luca")
                                                callback(false)
                                            }
                                        }
                                    )
                                }
                            }
                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                callback(false)
                            }
                        }
                    )
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    callback(false)
                }
            }
        )

    }

}


