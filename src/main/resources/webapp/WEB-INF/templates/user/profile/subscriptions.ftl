<html>
    <head>
        <title>Subscriptions</title>
    </head>
    <body>
        <h2>Your subscriptions:</h2>
        <#list subscriptions as subscription>
        <hr>
         <h3>${subscription.username}</h3>
        <hr>
        </#list>
    </body>
</html>