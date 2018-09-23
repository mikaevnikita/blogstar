<html>
    <head>
        <title>Subscriptions</title>
                <#import "../parts/pager.ftl" as p>

    </head>
    <body>
        <h2>Your subscriptions:</h2>
            <#list page.content as subscription>
                <hr>
                <h3>${subscription.username}</h3>
                <form action="/user/${subscription.username}/unsubscribe/">
                <input type="submit" value="Unsubscribe"/>
                </form>
                <hr>
            </#list>
        <@p.pager url page/>
    </body>
</html>