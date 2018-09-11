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