

## User



Representation of a user.





  
<article>

***address*** [`Address?`](/docs/commons-address--page#model) 

Address of the user.

</article>
<article>

***attributes*** `Map<String, String>` 

Platform-specific attributes assigned to the user

</article>
<article>

***creationDate*** `Long` 

Creation date of the user, as UNIX timestamp in milliseconds.

</article>
<article>

***email*** `String` 

Email address.

</article>
<article>

***familyName*** `String` 

Family name of the user.

</article>
<article>

***givenName*** `String` 

First name of the user.

</article>
<article>

***id*** [`UserId`](#userid) 

Identifier of the user.

</article>
<article>

***memberOf*** [`OrganizationRef?`](/docs/organization--page#organizationref) 

Organization Ref to which the user belongs.

</article>
<article>

***phone*** `String?` 

Telephone number of the user.

</article>
<article>

***roles*** `UserRoles` 

Roles of the user.

</article>
<article>

***sendEmailLink*** `Boolean?` 

Send a validation email to the user on subscription.

</article>

