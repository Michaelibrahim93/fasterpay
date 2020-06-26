package com.test.fasterpay.dataaccess.fakenetwork

import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.vo.User
import io.reactivex.Observable

interface FakeWebService {
    fun login(credentialsForm: CredentialsForm): Observable<User>
    fun signUp(credentialsForm: CredentialsForm, user: User): Observable<User>
}