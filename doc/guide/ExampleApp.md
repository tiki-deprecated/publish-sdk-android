---
title: Example App
excerpt: Getting started with the TIKI SDK Android sample application
category: 6389822a2e5635009bf8d88b
parentDoc: 63db61e394cb180218af22ba
slug: tiki-sdk-android-example-app
hidden: false
order: 2
---

To run the TIKI SDK Android sample application you need to build the TIKI SDK Android binaries first.

1.	Clone the TIKI SDK Android repository.

`git clone https://github.com/tiki/tiki-sdk-android.git`

2.	Open the TIKI SDK Android project in Android Studio.

3.	To download the dependencies from the TIKI SDK GitHub Maven repository, create a GitHub Token with the `read: packages` permission and add it to your `gradle.properties` file:

#### gradle.properties file:

```
gpr.key=<github token>
gpr.user=<github username>
```

Alternatively you can use the following environment variables instead of editing `gradle.properties`
```
GITHUB_USER
GITHUB_TOKEN
```

4.	Open the terminal and run the gradle assemble command in the repository's root.
   - In Windows: gradlew.bat assemble
   - In Linux/macOS: ./gradlew assemble

5.	Open the `example_app` folder as a Project in Android Studio

6.	Sync the Project with Gradle Files

7.	Run the app in an emulator or Physical device

## SDK initialization

The first initialization of the SDK is done without passing a wallet address as a parameter This tells the TIKI SDK to create a new wallet on startup.

#### example_app/app/src/main/java/com/mytiki/tiki_sdk_android/example_app/try_it_out/TryItOutViewModel.kt - lines 67 to 73

```
val apiId = "2b8de004-cbe0-4bd5-bda6-b266d54f5c90"
val origin = "com.mytiki.tiki_sdk_android.test"
val tikiSdk = TikiSdk().init(apiId, origin, context, address).await()
_wallets.value!!.put(tikiSdk.address, tikiSdk).apply {
    _wallets.postValue(_wallets.value)
}
_selectedWalletAddress.postValue(tikiSdk.address)
```

When the user presses the floating action button in the lower right hand corner of the Wallets screen, a new wallet is created. When TIKI SDK Android is initialized without an address, a new wallet is created with a random unique address saved in the list of wallets.

#### example_app/app/src/main/java/com/mytiki/tiki_sdk_android/example_app/wallet/WalletListFragment.kt - lines 41 to 43

```
binding.button.setOnClickListener {
    viewModel.loadTikiSdk(requireContext())
}
```

It is possible to switch wallets while using the application. This functionality is used to simulate SDK initialization on a device where the user has previously created a wallet or in a multi-tenant application. When a valid address is passed as a parameter a new TIKI SDK instance is created for the wallet.

_Note: The TIKI SDK requires a valid private key for the address, saved in its secure storage. This is managed automatically by the SDK for any wallets created locally._

#### example_app/app/src/main/java/com/mytiki/tiki_sdk_android/example_app/wallet/WalletListviewHolder.kt - lines 19 to 22

```
itemView.setOnClickListener {
    viewModel.loadTikiSdk(itemView.context, tikiSdkAddress)
    Navigation.findNavController(itemView).popBackStack()
}
```

**Important:** By design, the TIKI SDK does not save the addresses of created wallets. This information must be saved by the application in a local or remote persistence.

## Ownership NFT

When a new wallet is created, the application creates an Ownership NFT for the default data source using the `assignOwnership` call and passing said data source. The source parameter can be any String that uniquely identifies the data. For this example, we use a random UUID defined in the destination object.

#### example_app/app/src/main/java/com/mytiki/tiki_sdk_android/example_app/try_it_out/TryItOutViewModel.kt - lines 74 to 84

```
tikiSdk.assignOwnership(
    destination.value!!.source,
    TikiSdkDataTypeEnum.data_stream,
    listOf("generic data"),
    "Data destination created with TIKI SDK Sample App"
)
val ownership = tikiSdk.getOwnership(destination.value!!.source)
_ownerships.value!!.toMutableMap().apply {
    this.put(tikiSdk.address, ownership!!)
    _ownerships.postValue(this)
}
```

When changing the wallet or changing the body of the request, the Ownership of the new source is loaded. For this, we use a getter in the ViewModel

```
val ownership: TikiSdkOwnership?
    get() = ownerships.value?.get(selectedWalletAddress.value)
```

## Consent NFT

In the sample application, for convenience, we auto-create a Consent NFT on wallet initialization. The requested URL is used as the `TikiSdkDestination` path, and the method is marked as the use case.

#### example_app/app/src/main/java/com/mytiki/tiki_sdk_android/example_app/try_it_out/TryItOutViewModel.kt - lines 85 to 101

```
val path: String = URL(destination.value?.url).host!!
val use: String = destination.value!!.httpMethod
val destination = TikiSdkDestination(listOf(path), listOf(use))
val expiry: Calendar = Calendar.getInstance().apply {
    this.add(Calendar.YEAR, 10)
}
val consent: TikiSdkConsent = tikiSdk.modifyConsent(
    ownership!!.transactionId,
    destination,
    "Consent given to echo data in remote server",
    "Test the SDK",
    expiry.time
)
_consents.value!!.toMutableMap().apply {
    this.put(ownership.transactionId, consent)
    _consents.postValue(this)
}
```

Additional Consent NFTs are created when the user turns the _"toggle consent"_ switch on or off. When changing other destination data, the Consent NFT is not modified.

##### example_app/app/src/main/java/com/mytiki/tiki_sdk_android/example_app/try_it_out/TryItOutViewModel.kt - lines 108 to 134

```
fun toggleConsent() {
    val path: String = URL(destination.value!!.url).host
    val use: String = destination.value!!.httpMethod
    val destination = if (isConsentGiven.value!!) {
        TikiSdkDestination.NONE
    } else {
        TikiSdkDestination(listOf(path), listOf(use))
    }
    viewModelScope.launch {
        val consent: TikiSdkConsent = tikiSdk!!.modifyConsent(
            ownership!!.transactionId,
            destination,
            "Consent given to echo data in remote server",
            "Test the SDK",
            Calendar.getInstance().apply {
                add(Calendar.YEAR, 10)
            }.time
        )
        _consents.value!!.toMutableMap().apply {
            this.put(consent.ownershipId, consent)
            _consents.postValue(this)
        }
        isConsentGiven.value.apply {
            _isConsentGiven.postValue(!isConsentGiven.value!!)
        }
    }
}
```

## Outbound Requests

The example app executes an HTTP request in the time interval defined in the destination screen. Before each request, the app calls the `applyConsent` method from TIKI SDK. If Consent has been given, the request is executed. If not, the request is denied and a blocked message is showed in the log.

#### example_app/app/src/main/java/com/mytiki/tiki_sdk_android/example_app/try_it_out/TryItOutViewModel.kt - lines 136 to 206

```
fun makeRequest() {
    var logToAppend: TryItOutReq? = null
    if (tikiSdk == null) {
        logToAppend = TryItOutReq("🔴", "ERROR: Create a Wallet")
        _log.value!!.toMutableList().apply {
            this.add(logToAppend!!)
            _log.postValue(this)
        }
    } else {
        viewModelScope.launch {
            try {
                val url = URL(destination.value!!.url)
                val path: String = url.host
                val use: String = destination.value!!.httpMethod
                val destination = TikiSdkDestination(listOf(path), listOf(use))
                tikiSdk!!.applyConsent(this@TryItOutViewModel.destination.value!!.source, destination, {
                    viewModelScope.launch(Dispatchers.IO) {
                        val con = url.openConnection() as HttpURLConnection
                        con.requestMethod = use
                        val postData = this@TryItOutViewModel.destination.value!!.body.toByteArray()
                        con.doOutput = true
                        val wr = DataOutputStream(con.outputStream)
                        wr.write(postData)
                        wr.flush()
                        wr.close()
                        val responseCode = con.responseCode
                        val income = BufferedReader(InputStreamReader(con.inputStream))
                        val response = StringBuilder()
                        var inputLine = income.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = income.readLine()
                        }
                        income.close()
                        if (responseCode in 200..299) {
                            logToAppend = TryItOutReq(
                                "🟢",
                                "${responseCode}: $response"
                            )
                            _log.value!!.toMutableList().apply {
                                this.add(logToAppend!!)
                                _log.postValue(this)
                            }
                        } else {
                            logToAppend = TryItOutReq(
                                "🔴",
                                "${responseCode}: $response"
                            )
                            _log.value!!.toMutableList().apply {
                                this.add(logToAppend!!)
                                _log.postValue(this)
                            }
                        }
                    }
                }, {
                    logToAppend = TryItOutReq("🔴", "Blocked: consent required")
                    _log.value!!.toMutableList().apply {
                        this.add(logToAppend!!)
                        _log.postValue(this)
                    }
                })
            } catch (e: Exception) {
                logToAppend = TryItOutReq("🔴", e.message.toString())
                _log.value!!.toMutableList().apply {
                    this.add(logToAppend!!)
                    _log.postValue(this)
                }
            }
        }
    }
}
```

## Testing

The purpose of this sample app is for developers to try out the core features and become familiar with implementing the TIKI SDK in Android. Clone the repository and modify however you want. If you find any bugs, please create issues or send in PRs!

For any questions you can contact the team on [Discord](https://discord.gg/tiki). Have fun!

