# About
Using **query** with multiple interfaces and the spread sintax.

The query used:

<pre>
 query bestPet {
   favoritePet {
     name
     owner {
       firstName
     }
     ...on Dog {
       doesBark
     }
     ...on Cat{
       doesMeow
     }
   }
 }
</pre>
