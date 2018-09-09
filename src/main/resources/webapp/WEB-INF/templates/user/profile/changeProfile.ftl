<html>
<head>
        <#include "../../general.ftl">
</head>
<body>
<h1>Change profile info</h1>

<form action="/user/profile" method='POST' enctype="multipart/form-data">
    <table>
        <tr>
            <td>First name:</td>
            <td>
                <input type="text" name="firstName" value="${user.firstName}" />
                    <#if firstNameErrors??>
                        <#list firstNameErrors as error>
                            ${error}
                        </#list>
                    </#if>
            </td>

        </tr>
        <tr>
            <td>Last name:</td>
            <td>
                <input type='text' name='lastName' value="${user.lastName}"/>
                    <#if lastNameErrors??>
                        <#list lastNameErrors as error>
                            ${error}
                        </#list>
                    </#if>
            </td>

        </tr>
        <tr>
            <td>Profile photo:</td>
            <td>
                <img src="/img/${user.profilePhotoFilename}">
            </td>
            <td>
                <input type="file" name="profilePhoto"/>
                    <#if profilePhotoErrors??>
                        <#list profilePhotoErrors as error>
                            ${error}
                        </#list>
                    </#if>
            </td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Save changes" /></td>
        </tr>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </table>
</form>
</body>
</html>