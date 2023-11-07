<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            text-align: center;
        }

        h1 {
            color: #333;
        }

        h1.green {
            color: green;
        }

        h1.red {
            color: red;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
            max-width: 400px;
            margin: 0 auto;
        }

        fieldset {
            border: none;
            margin: 0;
            padding: 0;
        }

        legend {
            font-weight: bold;
        }

        table {
            width: 100%;
        }

        th {
            text-align: right;
            padding: 10px;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        button {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 3px;
        }

        button[type="reset"] {
            background-color: #ccc;
            color: #333;
        }

        a {
            text-decoration: none;
        }

        button.back-button {
            background-color: #333;
        }
    </style>
</head>
<body>
    <h1>This is admin page</h1>
    <h1 class="green">${pos}</h1>
    <h1 class="red">${neg}</h1>
    <form action="/admin/login" method="post">
        <fieldset>
            <legend>Login here</legend>
            <table>
                <tr>
                    <th>Email:</th>
                    <th><input type="email" name="email"></th>
                </tr>
                <tr>
                    <th>Password:</th>
                    <th><input type="password" name="password"></th>
                </tr>
                <tr>
                    <th><button>Login</button></th>
                    <th><button type="reset">Cancel</button></th>
                </tr>
            </table>
        </fieldset>
    </form>
    <br>
    <a href="/"><button class="back-button">Back</button></a>
</body>
</html>
