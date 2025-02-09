package com.rnlnd.helpers

import android.util.Log
import com.facebook.react.bridge.Promise
import com.google.protobuf.InvalidProtocolBufferException
import com.google.protobuf.MessageOrBuilder
import lndmobile.Callback
import lndmobile.RecvStream

class OpenChannelRecvStream(private val promise: Promise) : RecvStream {
  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "OpenChannelRecvStream onError: " + p0.message);
    promise.resolve(false);
  }

  public override fun onResponse(var1: ByteArray?) {
    Log.v("ReactNativeLND", "OpenChannelRecvStream onResponse");
    if (var1 != null) {
      val resp: lnrpc.Rpc.OpenStatusUpdate = lnrpc.Rpc.OpenStatusUpdate.parseFrom(var1);
      this.promise.resolve(respToJson(resp));
    } else {
      this.promise.resolve(true);
    }
  }
}

class CloseChannelRecvStream(private val promise: Promise) : RecvStream {
  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "CloseChannelRecvStream onError: " + p0.message);
    promise.resolve(false);
  }

  public override fun onResponse(var1: ByteArray?) {
    if (var1 != null) {
      val resp: lnrpc.Rpc.CloseStatusUpdate = lnrpc.Rpc.CloseStatusUpdate.parseFrom(var1);
      Log.v("ReactNativeLND", "CloseChannelRecvStream onResponse: " + respToJson(resp));
      this.promise.resolve(respToJson(resp));
    } else {
      this.promise.resolve(true);
    }
  }
}

class FundingStateStepCallback(private val promise: Promise) : Callback {
  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "FundingStateStepCallback onError: " + p0.message);
    this.promise.resolve(false);
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "FundingStateStepCallback success");
    if (p0 != null) {
      val resp: lnrpc.Rpc.FundingStateStepResp = lnrpc.Rpc.FundingStateStepResp.parseFrom(p0);
      this.promise.resolve(respToJson(resp));
    } else {
      this.promise.resolve(true);
    }
  }
}

class GetInfoCallback(promise: Promise) : Callback {
  private val promise = promise;

  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "GetInfoCallback onError");
    this.promise.resolve(false);
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "GetInfoCallback success");
    if (p0 != null) {
      val resp: lnrpc.Rpc.GetInfoResponse = lnrpc.Rpc.GetInfoResponse.parseFrom(p0);
      this.promise.resolve(respToJson(resp));
    } else {
      this.promise.resolve(false);
    }
  }
}


class ListChannelsCallback(private val promise: Promise) : Callback {
  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "ListChannelsCallback onError");
    this.promise.resolve(false);
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "ListChannelsCallback success");
    if (p0 != null) {
      val resp: lnrpc.Rpc.ListChannelsResponse = lnrpc.Rpc.ListChannelsResponse.parseFrom(p0);
      this.promise.resolve(respToJson(resp));
    } else {
      this.promise.resolve(false);
    }
  }
}

class ListPeersCallback(promise: Promise) : Callback {
  private val promise = promise;

  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "ListPeersCallback onError");
    this.promise.resolve(false);
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "ListPeersCallback success");
    if (p0 != null) {
      val resp: lnrpc.Rpc.ListPeersResponse = lnrpc.Rpc.ListPeersResponse.parseFrom(p0);
      this.promise.resolve(respToJson(resp));
    } else {
      this.promise.resolve(false);
    }
  }
}


class PendingChannelsCallback(promise: Promise) : Callback {
  private val promise = promise;

  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "PendingChannelsCallback onError");
    this.promise.resolve(false);
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "PendingChannelsCallback success");
    if (p0 != null) {
      val resp: lnrpc.Rpc.PendingChannelsResponse = lnrpc.Rpc.PendingChannelsResponse.parseFrom(p0);
      this.promise.resolve(respToJson(resp));
    } else {
      this.promise.resolve(false);
    }
  }
}


class EmptyResponseBooleanCallback(promise: Promise) : Callback {
  private val promise = promise;

  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "Callback onError");
    this.promise.resolve(false);
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "Callback success");
    this.promise.resolve(true);
  }
}

class StartCallback(promise: Promise) : Callback {
  private val promise = promise;

  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "start callback onError");
    this.promise.resolve(false);
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "lnd started ===========================================================================");
    this.promise.resolve(true);
  }
}

class StartCallback2 : Callback {
  public override fun onError(p0: Exception) {
    Log.v("ReactNativeLND", "start callback onError 2");
  }

  public override fun onResponse(p0: ByteArray?) {
    Log.v("ReactNativeLND", "lnd is ready ===========================================================================");
  }
}


class InitWalletCallback(promise: Promise) : Callback {
  private val promise = promise;

  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "lnd init err ===========================================================================" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "lnd inited ===========================================================================");
    try {
      if (bytes != null) {
        val resp: lnrpc.Walletunlocker.InitWalletResponse = lnrpc.Walletunlocker.InitWalletResponse.parseFrom(bytes);
        Log.v("ReactNativeLND", "resp: " + resp.toString());
      }
      this.promise.resolve(true);
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace()
      this.promise.resolve(false);
    }
  }
}


class UnlockWalletCallback(pr: Promise) : Callback {
  private val prr = pr;

  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "lnd unlock err ===========================================================================" + e.message);
    this.prr.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "lnd unlocked ===========================================================================");
    try {
      if (bytes != null) {
        val resp: lnrpc.Walletunlocker.UnlockWalletRequest = lnrpc.Walletunlocker.UnlockWalletRequest.parseFrom(bytes);
        Log.v("ReactNativeLND", "lnd unlock resp: " + resp.toString());
      }
      this.prr.resolve(true);
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.prr.resolve(false);
    }
  }
}


class WalletBalanceCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "WalletBalanceCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "WalletBalanceCallback ok");
    try {
      if (bytes != null) {
        val resp = lnrpc.Rpc.WalletBalanceResponse.parseFrom(bytes);
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}

class GetTransactionsCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "GetTransactionsCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "GetTransactionsCallback ok");
    try {
      if (bytes != null) {
        val resp = lnrpc.Rpc.TransactionDetails.parseFrom(bytes);
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}


class ChannelBalanceCallback(promise: Promise) : Callback {
  private val promise = promise;

  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "ChannelBalanceCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "ChannelBalanceCallback ok");
    try {
      if (bytes != null) {
        val resp: lnrpc.Rpc.ChannelBalanceResponse = lnrpc.Rpc.ChannelBalanceResponse.parseFrom(bytes);
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}


class GenSeedCallback(promise: Promise) : Callback {
  private val promise = promise;

  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "GenSeedCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "GenSeedCallback ok");
    try {
      if (bytes != null) {
        val resp: lnrpc.Walletunlocker.GenSeedResponse = lnrpc.Walletunlocker.GenSeedResponse.parseFrom(bytes);
        this.promise.resolve(resp.cipherSeedMnemonicList.joinToString(" "));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}


class SendPaymentSyncCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "SendPaymentSyncCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "SendPaymentSyncCallback ok");
    try {
      if (bytes != null) {
        val resp: lnrpc.Rpc.SendResponse = lnrpc.Rpc.SendResponse.parseFrom(bytes);
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}

class DecodePayReqCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "DecodePayReqCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "DecodePayReqCallback ok");
    try {
      if (bytes != null) {
        val resp: lnrpc.Rpc.PayReq = lnrpc.Rpc.PayReq.parseFrom(bytes);
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}

class QueryRoutesCallbackCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "QueryRoutesCallbackCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "QueryRoutesCallbackCallback ok");
    try {
      if (bytes != null) {
        val resp = lnrpc.Rpc.QueryRoutesResponse.parseFrom(bytes);
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}

class SendToRouteV2Callback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "SendtoRouteV2Callback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "SendtoRouteV2Callback ok");
    try {
      if (bytes != null) {
        val resp: lnrpc.Rpc.HTLCAttempt = lnrpc.Rpc.HTLCAttempt.parseFrom(bytes);
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}


class AddInvoiceCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "AddInvoiceCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "AddInvoiceCallback ok");
    try {
      if (bytes != null) {
        val resp: lnrpc.Rpc.AddInvoiceResponse = lnrpc.Rpc.AddInvoiceResponse.parseFrom(bytes)
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}


class ListPaymentsCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "ListPaymentsCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "ListPaymentsCallback ok");
    try {
      if (bytes != null) {
        val resp = lnrpc.Rpc.ListPaymentsResponse.parseFrom(bytes)
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}


class SendCoinsCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "SendCoinsCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "SendCoinsCallback ok");
    try {
      if (bytes != null) {
        val resp = lnrpc.Rpc.SendCoinsResponse.parseFrom(bytes)
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}

class ListInvoicesCallback(private val promise: Promise) : Callback {
  override fun onError(e: Exception) {
    Log.v("ReactNativeLND", "ListInvoicesCallback err" + e.message);
    this.promise.resolve(false);
  }

  override fun onResponse(bytes: ByteArray?) {
    Log.v("ReactNativeLND", "ListInvoicesCallback ok");
    try {
      if (bytes != null) {
        val resp = lnrpc.Rpc.ListInvoiceResponse.parseFrom(bytes)
        this.promise.resolve(respToJson(resp));
      } else {
        this.promise.resolve(false);
      }
    } catch (e: InvalidProtocolBufferException) {
      e.printStackTrace();
      this.promise.resolve(false);
    }
  }
}

fun byteArrayToHex(bytesArg: ByteArray): String {
  return bytesArg.joinToString("") { String.format("%02X", (it.toInt() and 0xFF)) }.toLowerCase()
}

fun hexStringToByteArray(strArg: String): ByteArray {
  val HEX_CHARS = "0123456789ABCDEF"
  val str = strArg.toUpperCase();

  if (str.length % 2 != 0) return hexStringToByteArray("");

  val result = ByteArray(str.length / 2)

  for (i in 0 until str.length step 2) {
    val firstIndex = HEX_CHARS.indexOf(str[i]);
    val secondIndex = HEX_CHARS.indexOf(str[i + 1]);

    val octet = firstIndex.shl(4).or(secondIndex)
    result.set(i.shr(1), octet.toByte())
  }

  return result
}

fun respToJson(resp: MessageOrBuilder): String {
  return com.google.protobuf.util.JsonFormat.printer().print(resp);
}
