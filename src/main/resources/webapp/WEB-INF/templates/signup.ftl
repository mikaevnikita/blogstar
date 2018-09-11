<html>
<head>
    <#include "general.ftl">
</head>
<body>
<h1>Create your Account</h1>
    <#if message??>
        <p>${message}</p>
    </#if>

    <form action="/signup" method='POST'>
        <table>
            <tr>
                <td>Username:</td>
                <td>
                    <input type="text" name="username" value="<#if form??>${form.username}</#if>">
                    <#if usernameErrors??>
                        <#list usernameErrors as error>
                            ${error}
                        </#list>
                    </#if>
                </td>

            </tr>
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
                <td>Date of birth:</td>
                <td>
                    <input type="date" name="dateOfBirth"
                           min="1900-01-01" max="${.now?date}"/>
                    <#if dateOfBirthErrors??>
                        <#list dateOfBirthErrors as error>
                            ${error}
                        </#list>
                    </#if>
                </td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>
                    <input type='email' name='email' value="<#if form??>${form.email}</#if>"/>
                    <#if emailErrors??>
                        <#list emailErrors as error>
                            ${error}
                        </#list>
                    </#if>
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <input type='password' name='password'/>
                    <#if passwordErrors??>
                        <#list passwordErrors as error>
                            ${error}
                        </#list>
                    </#if>
                </td>
            </tr>
            <tr>
                <td><input name="submit" type="submit" value="Sign up" /></td>
            </tr>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </table>
    </form>
</body>
</html>