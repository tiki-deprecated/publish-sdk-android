---
title: TikiSdk excerpt: The primary object for interacting with the TIKI infrastructure.
Use `TikiSdk` to assign ownership, modify, and apply consent. category: 6386a02f5b7bf00510590f34
slug: tiki-sdk-android-tiki-sdk hidden: false order: 1
---

## Constructors

##### TikiSdk (...)

Creates an empty TikiSdk. 

## Methods

##### init(...) &#8594; String

Initializes the TIKI SDK. It should be called before any other method. It sets up Flutter Engine and 
Platform Channel and builds the core of the TIKI SDK, calling TIKI SDK Dart through the Flutter 
Platform Channel.

Parameters:

- **apiId &#8594; String**
  A unique identifier for your account. Create, revoke, and cycle Ids (not a secret but try and
  treat it with care) at [console.mytiki.com](https://console.mytiki.com).


- **origin &#8594; String**  
  Included in the on-chain transaction to denote the application of origination (can be overridden
  in individual requests). It should follow a reversed FQDN syntax. i.e. com.mycompany.myproduct


- **context &#8594; [Context](https://developer.android.com/reference/android/content/Context)**  
  Set the application context. Required for
  the [MethodChannel](https://api.flutter.dev/flutter/services/MethodChannel-class.html) which
  communicates with the [Dart SDK](https://github.com/tiki/tiki-sdk-dart) binaries


- **address &#8594; String? = null**  
  Set the user address (primarily for restoring the state on launch). If not set, a new key pair and
  address will be generated for the user.

##### assignOwnership(...) &#8594; String

Data ownership can be assigned to any data point, pool, or stream, creating an immutable, on-chain
record.

Parameters:

- **source &#8594; String**  
  An identifier in your system corresponding to the raw data.  
  _i.e. a user_id_


- **type &#8594; String**  
  One of `"point"`, `"pool"`, or `"stream"`


- **contains &#8594; List&lt;String>**  
  A list of metadata tags describing the represented data


- **origin &#8594; String? = null**  
  An optional override of the default origin set during initialization


- **about &#8594; String? = null**  
  An optional description to provide additional context to the transaction. Most typically as
  human-readable text.

Returns:

- **String**  
  The unique transaction id (use to recall the transaction record at any time)

Example:

```
val tid = tiki.assignOwnership("12345", TikiSdkDataTypeEnum.data_point, listOf("email_address"))
```

&nbsp;

##### modifyConsent(...) &#8594; [TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent)

Consent is given (or revoked) for data ownership records. Consent defines "who" the data owner has
given utilization rights.

Parameters:

- **ownershipId &#8594; String**  
  The transaction id for the ownership grant


- **destination &#8594; [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination)**  
  A collection of paths and application use cases that consent has been granted (or revoked) for.


- **about &#8594; String? = null**  
  An optional description to provide additional context to the transaction. Most typically as
  human-readable text.


- **reward &#8594; String? = null**  
  An optional definition of a reward promised to the user in exchange for consent.


- **expiry
  &#8594; [LocalDateTime](https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/kotlinx.datetime/-local-date-time/-local-date-time.html)
  ? = null**  
  The date upon which the consent is no longer valid. If not set, consent is perpetual.

Returns:

- **[TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent)**  
  the modified `TikiSdkConsent`

Example:

```
val consent =  tiki.modifyConsent(oid, TikiSdkDestination(listOf("*"), listOf("*")))
```

&nbsp;

##### getOwnership(source: String, origin: String?) &#8594; [TikiSdkOwnership](tiki-sdk-android-tiki-sdk-ownership)?

Get the `TikiSdkOwnership` for a `source` and `origin`. If `origin` is unset, the default set during
construction is used.

Parameters:

- **source &#8594; String**  
  An identifier in your system corresponding to the raw data.  
  _i.e. a user_id_

- **origin &#8594; String? = null**  
  An optional override of the default origin set during initialization

Returns:

- **[TikiSdkOwnership](tiki-sdk-android-tiki-sdk-ownership)?**  
  the assigned `TikiSdkOwnership`

Example:

```
val ownership = tiki.getOwnership("12345")
```

&nbsp;

##### getConsent(source: String, origin: String?) &#8594; [TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent)?

Get the latest `TikiSdkConsent` for a `source` and `origin`. If `origin` is unset, the default set
during construction is used.

Parameters:

- **source &#8594; String**  
  An identifier in your system corresponding to the raw data.  
  _i.e. a user_id_

- **origin &#8594; String? = null**  
  An optional override of the default origin set during initialization

Returns:

- **[TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent)?**  
  the latest `TikiSdkConsent`

Example:

```
val consent = tiki.getConsent("12345")
```

&nbsp;

##### applyConsent(...)

Apply consent to a data transaction. If consent is granted for the `source` and `destination` and
has not expired, the request is executed.

Parameters:

- **source &#8594; String**  
  An identifier in your system corresponding to the raw data.  
  _i.e. a user_id_


- **destination &#8594; [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination)**  
  The destination(s) and use case(s) for the request.


- **request &#8594; () &#8594; Unit**  
  The function to execute if consent granted


- **onBlocked &#8594; (value: String) &#8594; Unit? = null**  
  An optional function to execute if consent is denied.


- **origin &#8594; String? = null**  
  An optional override of the default origin set during initialization

Example:

```
applyConsent("12345", TikiSdkDestination(listOf("*"), listOf("*")), { 
  print("Consent Approved. Send data to backend.")
})
```