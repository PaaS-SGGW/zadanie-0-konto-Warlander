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
                <th>id</th>
                <th>text</th>
            </tr>
            <#list texts as text>
            <tr>
                <td>${text.id}</td>
                <td>${text.text}</td>
            </tr>
            </#list>
        </table>
    </body>
</html>