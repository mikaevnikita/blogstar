<html>
    <head>
        Feed
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

        <#list posts as post>
             <p>${post.userDto.username} -> ${post.content}</p>
        </#list>
    </body>
</html>