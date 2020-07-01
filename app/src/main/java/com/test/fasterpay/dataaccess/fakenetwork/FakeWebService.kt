package com.test.fasterpay.dataaccess.fakenetwork

import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.vo.MoneyTransaction
import com.test.fasterpay.vo.PastTransaction
import com.test.fasterpay.vo.User
interface FakeWebService {
    suspend fun login(credentialsForm: CredentialsForm): User
    suspend fun signUp(credentialsForm: CredentialsForm, user: User): User
    suspend fun addTransaction(transaction: MoneyTransaction, walletId: Long): PastTransaction
}