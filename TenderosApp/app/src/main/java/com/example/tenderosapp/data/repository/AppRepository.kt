package com.example.tenderosapp.data.repository

import androidx.lifecycle.LiveData
import com.example.tenderosapp.data.client.AppClient
import com.example.tenderosapp.model.Promo
import com.example.tenderosapp.model.Transaction

//Singleton
class AppRepository {
    private val appClient : AppClient

    init {
        appClient = AppClient.getInstance()
    }

    companion object {
        private var instance: AppRepository? = null
        fun getInstance(): AppRepository {
            if (instance == null) {
                instance = com.example.tenderosapp.data.repository.AppRepository()
            }
            return instance as AppRepository
        }
    }

    fun getIsEmailRegistered(): LiveData<Boolean?> = appClient.getIsEmailRegistered()

    fun queryIsEmailRegistered(email : String) = appClient.queryIsEmailRegistered(email)

    fun queryRegisterPromo(promo : Promo, uid : String )  = appClient.queryRegisterPromo(promo, uid)
    fun getRegisterPromoSucccess(): LiveData<Boolean?> = appClient.getRegisterPromoSucccess()

    fun queryRegisterTransaction(transaction: Transaction, uid : String )  = appClient.queryRegisterTransaction(transaction, uid)
    fun getRegisterTransactionSucccess(): LiveData<Boolean?> = appClient.getRegisterPromoSucccess()

    fun getPromotionList() : LiveData<ArrayList<Promo>?> = appClient.getPromotionList()
    fun getTransactionList() : LiveData<ArrayList<Transaction>?> = appClient.getTransactionList()

    fun queryGetPromorionList(uid : String)  = appClient.queryGetPromorionList(uid)
    fun queryGetTransactionList(uid : String)  = appClient.queryGetTransactionList(uid)

}