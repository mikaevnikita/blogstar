<html>
<head>
        <#include "../../general.ftl">
</head>
<body>
<h1>Change profile info</h1>

<form action="/user/profile" method='POST'>
    <table>
        <tr>
            <td>First name:</td>
            <td>
                <input type="text" name="firstName" value="<#if form??>${form.firstName}</#if>" />
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
                <input type='text' name='lastName' value="<#if form??>${form.lastName}</#if>"/>
                    <#if lastNameErrors??>
                        <#list lastNameErrors as error>
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