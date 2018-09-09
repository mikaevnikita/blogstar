<html>
    <head>
        <title>Profile page</title>
        <#include "../../general.ftl">
    </head>
    <body>
        <h1>Hello, ${user.username}</h1>

        <img src="/img/${user.profilePhotoFilename}">

        <p>First name: ${user.firstName}</p>
        <p>Last name: ${user.lastName}</p>
        <p>Date of birth: ${user.dateOfBirth}</p>
        <p>About me: ${user.aboutMe}</p>

        <form action="/user/profile/changeProfile">
            <input type="submit" value="Change profile"/>
        </form>

        <form action="/user/profile/changePassword">
            <input type="submit" value="Change password"/>
        </form>

        <form action="/logout" method="POST">
            <input type="submit" value="Sign Out"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </body>
</html>