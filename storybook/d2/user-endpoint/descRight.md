

## Commands





  
<article>

**userCreate** ( cmd: [`UserCreateCommand`](#command) ) : [`UserCreatedEvent`](#event) <br/> *Access: im_write_user* 

Create a User.

</article>
<article>

**userResetPassword** ( cmd: [`UserResetPasswordCommand`](#command) ) : [`UserResetPasswordEvent`](#event) <br/> 

Send a reset password email to a given user.

</article>
<article>

**userUpdate** ( cmd: [`UserUpdateCommand`](#command) ) : [`UserUpdatedEvent`](#event) <br/> *Access: im_write_user* 

Update a User.

</article>
<article>

**userUpdateEmail** ( cmd: [`UserUpdateEmailCommand`](#command) ) : [`UserUpdatedEmailEvent`](#event) <br/> *Access: im_write_user* 

Set the given email for a given user.

</article>
<article>

**userUpdatePassword** ( cmd: [`UserUpdatePasswordCommand`](#command) ) : [`UserUpdatedPasswordEvent`](#event) <br/> *Access: im_write_user* 

Set the given password for a given user ID.

</article>
<article>

**userUploadLogo** ( cmd: [`UserUploadLogoCommand`](#command) , file: `FilePart` ) <br/> *Access: im_write_user* 

Upload a logo for a given user

</article>

