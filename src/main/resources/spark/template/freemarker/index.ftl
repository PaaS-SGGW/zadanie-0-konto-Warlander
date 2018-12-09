<#ftl strip_whitespace = true>

<#assign charset="UTF-8">

<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
    </head>
    <body>
        <table>
            <tr>
                <td width="20px">id</td>
                <td>text</td>
            </tr>
            <#list texts as text>
            <tr>
                <td width="20px">${text.id}</td>
                <td>${text.text}</td>
            </tr>
            </#list>
        </table>
    </body>
</html>