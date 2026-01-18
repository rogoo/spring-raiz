# About
Using **query** with **@QueryMapping** and _connections_ between objects with **@SchemaMapping**.

The query used:

<pre>
 query bestPet {
   favoritePet {
     name
     owner {
       id
       firstName
     }
   }
 }
</pre>
