---
title: TikiSdkDestination 
excerpt: Defines destinations and use cases (optional) allowed or disallowed. Serializable for inclusion in transactions. 
category: 6386a02f5b7bf00510590f34 
slug: tiki-sdk-android-tiki-sdk-destination 
hidden: false 
order: 2
---

## Constructors

##### TikiSdkDestination(uses: List&lt;String>, paths: List&lt;String>)

Creates a new destination from a list of `paths` and `uses`.

## Types

##### ALL : TikiSdkDestination

Create a TikiSdkDestination allowing all paths and uses (`[*]`)

##### NONE : TikiSdkDestination

Create a TikiSdkDestination disallowing all paths and uses (`[]`)

## Properties

##### uses &#8594; List&lt;String>

A list of application specific uses cases applicable to the given destination. Prefix with NOT to
invert.  
_i.e. NOT ads_

##### paths &#8594; List&lt;String>

A list of paths, preferably URLs (without the scheme) or reverse FQDN. Keep list short and use
wildcard matching. Prefix with NOT to invert.  
_i.e. NOT mytiki.com/