<html>
  <head><title>Rantz</title></head>

  <body>
    <h2>Rantz:</h2>
    
    <a href="addRant.htm">Add rant</a><br/>
    <a href="register.htm">Register new motorist</a><br/>
    <ul>
    <#list rants as rant>  
    <li>${rant.vehicle.state}/
        ${rant.vehicle.plateNumber} -- 
        ${rant.rantText}
    </#list>
    </ul>
  </body>
</html>
