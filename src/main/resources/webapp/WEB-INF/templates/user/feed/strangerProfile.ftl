<html>
    <head>
                <#import "../parts/pager.ftl" as p>
    </head>
    <body>
        <h2>Posts by ${user.username}: </h2>
        <#list page.content as post>
             <p>${post.userDto.username} -> ${post.content}</p>
        </#list>
        <@p.pager url page/>

    </body>
</html>
