---
title: TikiSdkOwnership
excerpt: A Ownership Object. Representative of the NFT created on-chain. 
category: 6386a02f5b7bf00510590f34 
slug: tiki-sdk-android-tiki-sdk-ownership 
hidden: false 
order: 4
---

## Constructors

##### TikiSdkOwnership(...)

Parameters:

- **source → String** 
- **type → [TikiSdkDataTypeEnum](tiki-sdk-android-tiki-sdk-data-type-enum)** 
- **origin → String**
- **transactionId → String**
- **contains → List&lt;String> = listOf()**
- **about → String? = null**

## Properties

##### source &#8596; String

An identifier in your system corresponding to the raw data.
_i.e. a user_id_
_read / write_

##### type &#8596; [TikiSdkDataTypeEnum](tiki-sdk-android-tiki-sdk-data-type-enum)

`data_point`, `data_pool`, or `data_stream`  
_read / write_

##### origin &#8596; String

The origin from which the data was generated.
_read / write_

##### transactionId &#8596; String

The transaction id for `this` 
_read / write_

##### contains &#8596; List&lt;String> = listOf()

A list of metadata tags describing the represented data.
_read / write_

##### about &#8596; String? = null

An optional description to provide additional context to the transaction, typically as human-readable text.
_read / write_