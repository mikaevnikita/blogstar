<html>
<head>
    <title>Profile page</title>
        <#include "../../general.ftl">
</head>
<body>
<img src="/img/${user.profilePhotoFilename}">

<p>First name: ${user.firstName}</p>
<p>Last name: ${user.lastName}</p>
<p>Date of birth: ${user.dateOfBirth}</p>
<p>About me: ${user.aboutMe}</p>

<#if subscribed>
    <form action="/user/${user.username}/unsubscribe">
        <input type="submit" value="Unsubscribe"/>
    </form>

    <#else>
        <form action="/user/${user.username}/subscribe">
            <input type="submit" value="Subscribe"/>
        </form>
</#if>

<#if message??>
    <p>${message}</p>
</#if>

<form action="/user/feed/${user.username}">
    <input type="submit" value="Show posts"/>
</form>

</body>
</html>