---
title: Getting Started
excerpt: See just how easy (and fast) it is to add TIKI to your Android app —drop in a data exchange to increase user opt-ins and lower risk.
category: 6389822a2e5635009bf8d88b
slug: tiki-sdk-android-getting-started
hidden: false
order: 1
next:
  pages:
    - type: ref
      icon: book
      name: View the entire API
      slug: tiki-sdk-android-tiki-sdk
      category: SDK [Android]
---

### Installation

If you haven't already, add Maven Central to your Project's `build.gradle` (`PROJECT_ROOT/build.gradle`) 

``` 
repositories {
    mavenCentral()
}
```

Add the dependency in your App's `build.gradle` file (`PROJECT_ROOT/app/build.gradle`)
```
implementation("com.mytiki.tiki-sdk-android:0.0.3")
```

### Usage

#### 1. [Sign up](https://console.mytiki.com) (free) for a TIKI developer account to get an API ID.

#### 2. Construct the [TIKI SDK](tiki-sdk-android-tiki-sdk)

Configuration parameters:

- **apiId &#8594; String**   
A unique identifier for your account. Create, revoke, and cycle Ids _(not a secret but try and treat it with care)_ at https://mytiki.com.


- **origin &#8594; String**  
Included in the on-chain transaction to denote the application of origination (can be overridden in individual requests). It should follow a reversed FQDN syntax. _i.e. com.mycompany.myproduct_

  
- **context &#8594; [Context](https://developer.android.com/reference/android/content/Context)**   
Set the application context. Required for the [MethodChannel](https://api.flutter.dev/flutter/services/MethodChannel-class.html) which communicates with the [Dart SDK](https://github.com/tiki/tiki-sdk-dart) binaries


Example:

```
val tiki = TikiSdk("565b3268-cdc0-4e5c-94c8-5d8f53d4577c", "com.mycompany.myproduct", context)
```

#### 3. Assign ownership
Data ownership can be assigned to any data point, pool, or stream, creating an immutable, on-chain record.

Parameters:
- **source &#8594; String**  
An identifier in your system corresponding to the raw data. _i.e. a user_id_


- **type &#8594; String**  
`"point"`, `"pool"`, or `"stream"`


- **callback &#8594; (ownershipId: String) &#8594; Unit?**  
A callback function to execute on completion. Input (**String**) is the unique transaction id (use to recall the transaction record at any time)


- **about &#8594; String?**  
An optional description to provide additional context to the transaction. Most typically as human-readable text.


- **contains &#8594; List&lt;String>**
A list of metadata tags describing the represented data


- **origin &#8594; String?**  
An optional override of the default origin set during initialization


Example:

```
val callback = { response: String -> print(response) }
tiki.assignOwnership("12345", "point", callback, listOf("email_address"))
```

#### 4. Modify consent
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
});
```

#### 5. Apply consent
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
});
```