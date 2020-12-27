Class Shop.Wholesaling Extends (Shop.Client, %Persistent, %Populate)
{

Property NameCompany As %String;

Property Address As Shop.Address;

Property Accounts As array Of Shop.Account;

Method %OnNew(type As Shop.ClientType, nameCompany As %String, description As %String, address As Shop.Address, arrayOfAccounts As %Library.ArrayOfObjects) As %Status
{
	set ..Type = ##class(Shop.ClientType).Wholesaling()
	set ..Description = description
	set ..NameCompany = nameCompany
	set ..Address = address
	set ..Accounts = arrayOfAccounts
	
	QUIT $$$OK
}

Storage CacheStorage
{
<Data name="Accounts">
<Attribute>Accounts</Attribute>
<Structure>subnode</Structure>
<Subscript>"Shop.Wholesaling.Accounts"</Subscript>
</Data>
<Data name="WholesalingDefaultData">
<Subscript>"Wholesaling"</Subscript>
<Value name="1">
<Value>NameCompany</Value>
</Value>
<Value name="2">
<Value>Address</Value>
</Value>
</Data>
<DefaultData>WholesalingDefaultData</DefaultData>
<Type>%Storage.Persistent</Type>
}

Storage Default
{
<Data name="Accounts">
<Attribute>Accounts</Attribute>
<Structure>subnode</Structure>
<Subscript>"Shop.Wholesaling.Accounts"</Subscript>
</Data>
<Data name="WholesalingDefaultData">
<Subscript>"Wholesaling"</Subscript>
<Value name="1">
<Value>NameCompany</Value>
</Value>
<Value name="2">
<Value>Address</Value>
</Value>
</Data>
<DefaultData>WholesalingDefaultData</DefaultData>
<Type>%Library.CacheStorage</Type>
}

}
