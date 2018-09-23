<#macro pager url page>
    <div>
        <#assign currPage = page.getNumber()/>
        <#assign totalPages = page.getTotalPages()/>
        <#if currPage != 0>
            <form action="${url}" method="get">
                <input type="hidden" name="page" value="${currPage-1}" />
                <input type="submit" value="Previous"/>
            </form>
        </#if>
        <#if currPage != totalPages>
            <form action="${url}" method="get">
                <input type="hidden" name="page" value="${currPage+1}" />
                <input type="submit" value="Next"/>
            </form>
        </#if>
    </div>
</#macro>