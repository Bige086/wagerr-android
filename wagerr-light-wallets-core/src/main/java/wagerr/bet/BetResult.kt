package wagerr.bet

import android.text.TextUtils
import global.WagerrCoreContext
import org.apache.commons.codec.binary.Hex
import org.wagerrj.core.Transaction
import java.nio.charset.Charset


//3|1.0|#453|RUS
data class BetResult(val txType: TxType, val protocolVersion: String, val eventId: String,
                     val betResult: String, val transaction: Transaction)

fun BetResult.isRefund(): Boolean {
    return this.txType == TxType.TxTypeRefund
}

fun Transaction.toBetResult(): BetResult? {
    if (this.isBetResult()) {
        val items = this.getBetResultString().split("|")
        if (items[0] == "3") {
            return BetResult(TxType.TxTypeResult, items[1], items[2], items[3], this)
        } else {
            return BetResult(TxType.TxTypeRefund, items[1], items[2], items[3], this)
        }
    } else {
        return null
    }
}

fun List<Transaction>.toBetResults(): List<BetResult>? {
    return this.filter {
        it.updateTime.time > WagerrCoreContext.ORACLE_BET_EVENT_START_TIME
    }.mapNotNull {
        it.toBetResult()
    }
}

fun Transaction.isBetResult(): Boolean {
    return this.getBetResultString().isValidBetResultSource()
}

fun Transaction.getBetResultString(): String {
    val items = this.outputs.filter {
        it.scriptPubKey.isOpReturn
    }
    if (items.isEmpty()) {
        return ""
    }
    val hexString = items[0].toString().substring(items[0].toString().indexOf("[") + 1, items[0].toString().indexOf("]"))
    val bytes = Hex.decodeHex(hexString.toCharArray())
    val string = String(bytes, Charset.forName("UTF-8"))
    return string
}

fun String.isValidBetResultSource(): Boolean {
    if (TextUtils.isEmpty(this)) {
        return false
    }
    if (this.contains(",")) {
        return false
    }
    if (!(this.startsWith("3") || this.startsWith("4"))) {
        return false
    }

    if (this.split("|").size != 4) {
        return false
    }

    for (s in this.split("|")) {
        if (TextUtils.isEmpty(s)) {
            return false
        }
    }
    return true
}

