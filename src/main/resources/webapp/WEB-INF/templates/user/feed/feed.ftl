<html>
    <head>
        Feed
        <#import "../parts/pager.ftl" as p>
    </head>
    <body>
        <form action="/user/feed" method="post" enctype="multipart/form-data">
            <textarea rows="10" cols="24" name="content"></textarea>
            <input name="submit" type="submit" value="Post" />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
        <#if contentErrors??>
            <#list contentErrors as error>
                <p>Error: ${error}</p>
            </#list>
        </#if>

        <#list page.content as post>
            <p>${post.userDto.username} posted ${post.content} at ${post.dateTime}</p>
        </#list>
        <@p.pager url page/>

    </body>
</html>