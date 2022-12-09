---
title: TikiSdk
excerpt: The primary object for interacting with the TIKI infrastructure. Use `TikiSdk` to assign ownership, modify, and apply consent.
category: 6386a02f5b7bf00510590f34
slug: tiki-sdk-android-tiki-sdk
hidden: false
order: 1
---

## Constructors

##### TikiSdk (apiId: String, origin: String, context: [Context](https://developer.android.com/reference/android/content/Context))

## Methods

##### assignOwnership(source: String, type: String, callback: (ownershipId: String) &#8594; Unit? = null, contains: List&lt;String>? = null, about: String? = null, origin: String? = null)
Data ownership can be assigned to any data point, pool, or stream, creating an immutable, on-chain record.  

Parameters:
- **source &#8594; String**  
An identifier in your system corresponding to the raw data.  
_i.e. a user_id_


- **type &#8594; String**  
One of `"point"`, `"pool"`, or `"stream"`


- **callback &#8594; (ownershipId: String) &#8594; Unit?**
A callback function to execute on completion. Input (**String**) is the unique transaction id (use to recall the transaction record at any time)


- **contains &#8594; List&lt;String>**  
A list of metadata tags describing the represented data


- **origin &#8594; String?**  
An optional override of the default origin set during initialization


- **about &#8594; String?**  
An optional description to provide additional context to the transaction. Most typically as human-readable text.

Example:

```
val callback = { response: String -> print(response) }
tiki.assignOwnership("12345", "point", callback, listOf("email_address"))
```

&nbsp;

##### modifyConsent(ownershipId: String, destination: [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination), callback: (TikiSdkConsent) -> Unit? = null, about: String? = null, reward: String? = null, expiry: [Calendar](https://developer.android.com/reference/kotlin/java/util/Calendar.html)? = null) 
Consent is given (or revoked) for data ownership records. Consent defines "who" the data owner has given utilization rights.

Parameters:
- **ownershipId &#8594; String**  
The transaction id for the ownership grant


- **destination &#8594; [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination)**  
A collection of paths and application use cases that consent has been granted (or revoked) for.


- **callback &#8594; ([TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent) &#8594; Unit?)**
A callback function executed on completion. Input (**[TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent)**) is the modified consent.


- **about &#8594; String?**  
An optional description to provide additional context to the transaction. Most typically as human-readable text.


- **reward &#8594; String?**  
An optional definition of a reward promised to the user in exchange for consent.


- **expiry &#8594; [Calendar](https://developer.android.com/reference/kotlin/java/util/Calendar.html)?**  
The date upon which the consent is no longer valid. If not set, consent is perpetual.

Example:
```
tiki.modifyConsent(oid, TikiSdkDestination(listOf("*"), listOf("*")), { 
  response: TikiSdkConsent -> print(response) 
})
```

&nbsp;

##### getConsent(source: String, callback: ([TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent)) -> Unit? = null, origin: String?)  
Get the latest `TikiSdkConsent` for a `source` and `origin`. If `origin` is unset, the default set during construction is used.

Parameters:
- **source &#8594; String**  
  An identifier in your system corresponding to the raw data.  
  _i.e. a user_id_


- **callback &#8594; ([TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent) &#8594; Unit?)**
  A callback function executed on completion. Input (**[TikiSdkConsent](tiki-sdk-android-tiki-sdk-consent)**) is the returned consent.


- **origin &#8594; String?**  
An optional override of the default origin set during initialization

Example:
```
tiki.getConsent("12345", {
  response: TikiSdkConsent -> print(response)
});
```

&nbsp;

##### applyConsent(source: String, destination: [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination), request: (String) &#8594; Unit, onBlocked: (String) &#8594; Unit) 
Apply consent to a data transaction. If consent is granted for the `source` and `destination` and has not expired, the request is executed.

Parameters:
- **source &#8594; String**  
An identifier in your system corresponding to the raw data.  
_i.e. a user_id_


- **destination &#8594; [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination)**  
The destination(s) and use case(s) for the request.


- **request &#8594; ((String) &#8594; Unit)**  
The function to execute if consent granted


- **onBlocked &#8594; ((String) &#8594; Unit)**  
An optional function to execute if consent is denied.


Example:
```
applyConsent("12345", TikiSdkDestination(listOf("*"), listOf("*")), { 
  _: String -> print("Consent Approved. Send data to backend.")
})
```