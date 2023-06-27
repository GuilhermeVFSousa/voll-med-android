package com.gvfs.vollmed.features.patient

import android.os.Build
import androidx.annotation.RequiresApi
import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.config.httpInterceptor.AuthInterceptor
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.patient.domain.PageablePatient
import com.gvfs.vollmed.features.patient.domain.PatientCreate
import com.gvfs.vollmed.features.patient.domain.PatientResume
import com.gvfs.vollmed.features.shareddomain.Endereco
import com.gvfs.vollmed.features.shareddomain.ViaCep
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.call.receive
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.printStack
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class PatientService @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPatients(): List<PatientResume> {
        try {
            val response = authInterceptor.clientWithAuthorizationToken.get<PageablePatient> {
                url("${Constants.BASE_URL}${Constants.PATIENT_ENDPOINT}")
            }
            println("::::::::::::::::::: ${response.content}")
            return response.content
        } catch (e: ClientRequestException) {
            throw HttpResponseErrorException()
        } catch (e: Exception) {
            throw HttpResponseErrorException()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createPatient(patient: PatientCreate): Boolean {
        return try {
            authInterceptor.clientWithAuthorizationToken.post<PatientCreate> {
                url("${Constants.BASE_URL}${Constants.PATIENT_ENDPOINT}")
                body = patient
            }
            true
        } catch (e: ClientRequestException) {
            e.printStack()
            false
        } catch (e: Exception) {
            e.printStack()
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCEP(cep: String): Endereco {
        try {
            val response = authInterceptor.client.get<HttpResponse>{
                url("${Constants.VIA_CEP_URL}$cep/json/")
            }
            val responseJson = response.receive<JsonElement>()
            val stringResponse = responseJson.toString();
            val check = stringResponse.contains("cep")

            var endereco = Endereco(
                "",
                "",
                "",
                "",
                "",
                "",
                "")

            if (check && response.status == HttpStatusCode.OK) {
                val viaCepSuccess = responseJson.toViaCepSuccessResponse()
                endereco.logradouro = viaCepSuccess.logradouro
                endereco.bairro = viaCepSuccess.bairro
                endereco.cep = viaCepSuccess.cep
                endereco.cidade = viaCepSuccess.localidade
                endereco.uf = viaCepSuccess.uf

            }
            return endereco

        } catch (e: ClientRequestException) {
            throw HttpResponseErrorException()
        } catch (e: Exception) {
            throw HttpResponseErrorException()
        }
    }

    private fun JsonElement.toViaCepSuccessResponse(): ViaCep {
        val jsonObject = this as JsonObject
        return ViaCep(
            jsonObject["cep"]!!.jsonPrimitive.content,
            jsonObject["logradouro"]!!.jsonPrimitive.content,
            jsonObject["complemento"]!!.jsonPrimitive.content,
            jsonObject["bairro"]!!.jsonPrimitive.content,
            jsonObject["localidade"]!!.jsonPrimitive.content,
            jsonObject["uf"]!!.jsonPrimitive.content,
            jsonObject["ibge"]!!.jsonPrimitive.content,
            jsonObject["gia"]!!.jsonPrimitive.content,
            jsonObject["ddd"]!!.jsonPrimitive.content,
            jsonObject["siafi"]!!.jsonPrimitive.content
        )
    }

}