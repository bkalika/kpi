Class Shop.Address Extends (%Persistent, %Populate)
{

Property Code As %String(POPSPEC = ".MakeCode()");

Property Country As %String(POPSPEC = "##class(Shop.BuildingHelper).Countries()");

Property City As %String(POPSPEC = "##class(Shop.BuildingHelper).Cities()");

Property Street As %String;

Property Building As %String(POPSPEC = "##class(Shop.BuildingHelper).RandomBuilding()");

Method %OnNew(code As %String, country As %String, city As %String, street As %String, building As %String) As %Status
{
	set ..Code = code
	set ..Country = country
	set ..City = city
	set ..Street = street
	set ..Building = building
	QUIT $$$OK
}

Method toString()
{
	write ..Code
	write ..Country
	write ..City 
	write ..Street 
	write ..Building
}

// embedded:

Method PrintData() As %Integer
{
	set addressId =..%Id()
	&sql(declare AC cursor for
			SELECT Country, City, Street, Building
			into :country, :city, :street, :building
			FROM Shop.Address WHERE ID=:accountID)
			
			&sql(open AC)
			
			for {
				&sql(fetch AC)
				quit:(SQLCODE '=0)
				write !, %ROWCOUNT, ?20, country, ?50, city, ?30, street, ?20, building
				}
			&sql(close AC)
}

// dynamic:

ClassMethod FindIdByCity(city As %String) As %String
{
	&Sql(SELECT Id into :id 
	FROM Shop.Address WHERE City =:city)
	if SQLCODE quit $$$NULLOREF
	quit ##class(Shop.Address).%OpenId(id)
}

Storage Default
{
<Data name="AddressDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>Code</Value>
</Value>
<Value name="3">
<Value>Country</Value>
</Value>
<Value name="4">
<Value>City</Value>
</Value>
<Value name="5">
<Value>Street</Value>
</Value>
<Value name="6">
<Value>Building</Value>
</Value>
</Data>
<DataLocation>^Shop.AddressD</DataLocation>
<DefaultData>AddressDefaultData</DefaultData>
<IdLocation>^Shop.AddressD</IdLocation>
<IndexLocation>^Shop.AddressI</IndexLocation>
<StreamLocation>^Shop.AddressS</StreamLocation>
<Type>%Library.CacheStorage</Type>
}

}
