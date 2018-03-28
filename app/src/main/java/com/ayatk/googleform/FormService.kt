package com.ayatk.googleform

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FormService {

  @POST("/forms/d/e/1FAIpQLSeE52pcZ3cNRGTmDIdVDGEKBQlD-YOo62_fCh1Q1OXpnEvUmQ/formResponse")
  @FormUrlEncoded
  fun postForm(@Field("entry.134763060") content: String): Single<Response<Void>>

}
