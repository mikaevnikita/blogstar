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
        <div>
            <div class="g-recaptcha" data-sitekey="6LfuoW8UAAAAACxJHlhrFkg_NxZ7I0RMPpIJGCCf"></div>
                <#if captchaErrors??>
                    <#list captchaErrors as captchaError>
                        ${captchaError}
                    </#list>
                </#if>
        </div>
    </table>
</form>
</body>
</html>