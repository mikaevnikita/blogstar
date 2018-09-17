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

<#if message??>
    <p>${message}</p>
</#if>

<h2>Posts: </h2>
        <#list posts as post>
             <p>${post.userDto.username} -> ${post.content}</p>
        </#list>
</body>
</html>