# About
Using **query** with ***@Arguments***. As I am calling the same method twice, im using Aliases (or else error).

The query used:

<pre>
query bestPet {
  firstAlias: favoritePetByName(name:"r")  {
  	name
	}
  secondAlias: favoritePetByName(name:"tr")  {
  	name
    owner {
      firstName
    }
  }
}
</pre>

If the argument field has the same name in the Controller as in the schema, Spring will automatically map it. If not, you have define the name.

In this example in the schema the parameter name is **"name"**, but as we mapped as **"anotherName"**, we need to set it in the ***@Argument***.
<pre>
    @QueryMapping
    public List<Pet> favoritePetByName(@Argument("name") String anotherName) {
        return listPet.stream().filter(e -> e.name().contains(anotherName)).collect(Collectors.toList());
    }
</pre>
