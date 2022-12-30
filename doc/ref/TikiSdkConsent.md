---
title: TikiSdkConsent 
excerpt: A Consent Object. Representative of the NFT created on-chain. Requires a corresponding Data Ownership NFT (see [TikiSdk](tiki-sdk-android-tiki-sdk)). 
category: 6386a02f5b7bf00510590f34 
slug: tiki-sdk-android-tiki-sdk-consent 
hidden: false 
order: 3
---

## Constructors

##### TikiSdkConsent(ownershipId: String, destination: [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination), about: String, reward: String, transactionId: String, expiry: [Calendar](https://developer.android.com/reference/kotlin/java/util/Calendar.html)})

Builds a TikiSdkConsent for the data identified by `ownershipId`.

## Properties

##### ownershipId &#8596; String

The data ownership transaction ID corresponding to the data source consent applies to.  
_read / write_

##### destination &#8596; [TikiSdkDestination](tiki-sdk-android-tiki-sdk-destination)

The destination describing the allowed/disallowed paths and use cases for the consent.  
_read / write_

##### reward &#8596; String

An optional description of the reward owed to user in exchange for consent.
_read / write_

##### about &#8596; String

An optional description to provide additional context to the transaction. Most typically as
human-readable text.  
_read / write_

##### transactionId &#8596; String

The transaction id for `this`  
_read / write_

##### expiry &#8596; [LocalDateTime](https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/kotlinx.datetime/-local-date-time/-local-date-time.html)

The date the consent is valid until. Do not set (e.g., `null`) for perpetual consent.
_read / write_