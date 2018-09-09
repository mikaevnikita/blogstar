<html>
<head>
        <#include "../../general.ftl">
</head>
<body>
<h1>Change password</h1>

<form action="/user/profile/changePassword" method='POST'>
    <table>
        <tr>
            <td>New password:</td>
            <td>
                <input type='password' name='newPassword'/>
                    <#if newPasswordErrors??>
                        <#list newPasswordErrors as error>
                            ${error}
                        </#list>
                    </#if>
            </td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Change password" /></td>
        </tr>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </table>
</form>
</body>
</html>