Class Shop.BuildingHelper Extends %Persistent
{

ClassMethod DefaultString() As %String
{
	quit "DefaultString"
}

ClassMethod DefaultName() As %Status
{
	quit "1"
}

ClassMethod DefaultInteger() As %Integer
{
	quit 0
}

ClassMethod DefaultAmount() As %Integer
{
	quit 100
}

ClassMethod RandomBuilding() As %Status
{
	quit $Random(200)
}

ClassMethod SomeDate() As %Date
{
	s dates = $lb($zdateh("15/03/1994", 4), $zdateh("15/03/1999", 4), $zdateh("15/03/1999", 4))
	return $li(dates, $r($ll(dates)) +1)
}

ClassMethod Countries() As %String
{
 s countries = $lb("Ukraine", "Poland", "Germany")
 return $li(countries, $r($ll(countries)) + 1)
}

Storage Default
{
<Data name="BuildingHelperDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
</Data>
<DataLocation>^Shop.BuildingHelperD</DataLocation>
<DefaultData>BuildingHelperDefaultData</DefaultData>
<IdLocation>^Shop.BuildingHelperD</IdLocation>
<IndexLocation>^Shop.BuildingHelperI</IndexLocation>
<StreamLocation>^Shop.BuildingHelperS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
