<html>
<head>
<#include "general.ftl">
</head>
<body>
<div class="container">
    <h1>Sign in</h1>
    <#if message??>
    <p>${message}</p>
    </#if>
    <form action="/signin" method='POST'>
        <table>
            <tr>
                <td>Username:</td>
                <td><input type='text' name='username' value=''></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password' /></td>
            </tr>
            <tr>
                <td><input name="submit" type="submit" value="Sign in" /></td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" id="remember_me" name="remember_me" checked />
                    <label for="remember_me">Keep me logged in</label>
                </td>
            </tr>


            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </table>
    </form>
</div>

</body>
</html>