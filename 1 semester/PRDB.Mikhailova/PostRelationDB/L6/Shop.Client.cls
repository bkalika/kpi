Class Shop.Client Extends %Persistent [ Abstract ]
{

Property UniqueId As %String;

Property Type As Shop.ClientType;

Property Description As %String;

Relationship Orders As Shop.Order [ Cardinality = many, Inverse = Client ];

Storage Default
{
<Data name="ClientDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>UniqueId</Value>
</Value>
<Value name="3">
<Value>Type</Value>
</Value>
<Value name="4">
<Value>Description</Value>
</Value>
</Data>
<DataLocation>^Shop.ClientD</DataLocation>
<DefaultData>ClientDefaultData</DefaultData>
<IdLocation>^Shop.ClientD</IdLocation>
<IndexLocation>^Shop.ClientI</IndexLocation>
<StreamLocation>^Shop.ClientS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
