<#import "/spring.ftl" as spring />
<html>
  <head>
      <title>Add Rant</title>
      <style>
         .error {
             color: #ff0000;
             font-weight: bold;
         }
      </style>
  </head>
  
  <body>
    <h2>Enter a rant...</h2>
    <form method="POST" action="addRant.htm">
      <b>State: </b><@spring.formInput "rant.vehicle.state", "" /><br>
      <b>Plate #: </b><@spring.formInput "rant.vehicle.plateNumber", "" /><br>
      <@spring.formTextarea "rant.rantText", "row='5' cols='50'" />
      <input type="submit"/>
    </form>
  </body>
</html>
