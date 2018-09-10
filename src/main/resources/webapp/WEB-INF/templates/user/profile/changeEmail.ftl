<html>
<head>
        <#include "../../general.ftl">
</head>
<body>
<h1>Change email</h1>

<form action="/user/profile/changeEmail" method='POST'>
    <table>
        <tr>
            <td>New email:</td>
            <td>
                <input type='email' name='newEmail' value="<#if form??>${form.newEmail}</#if>"/>
                    <#if newEmailErrors??>
                        <#list newEmailErrors as error>
                            ${error}
                        </#list>
                    </#if>
            </td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Change email" /></td>
        </tr>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </table>
</form>
</body>
</html>