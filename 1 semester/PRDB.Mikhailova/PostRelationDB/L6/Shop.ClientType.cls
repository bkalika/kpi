Class Shop.ClientType Extends (%SerialObject, %Persistent, %Populate)
{

Property Name As %String;

ClassMethod Retailing() As Shop.ClientType
{
	set type = ##class(Shop.ClientType).%New()
	set type.Name = "Retailing"
	
	If ($isObject(type))
	{
		QUIT type
	}
	Else
	{
		QUIT ""
	}
}

Method toString()
{
	Write ..Name
}

ClassMethod Wholesaling() As Shop.ClientType
{
	set type = ##class(Shop.ClientType).%New()
	set type.Name = "Wholesaling"
	
	If ($isObject(type))
	{
		QUIT type
	}
	Else
	{
		QUIT ""
	}
}

Storage Default
{
<Data name="ClientTypeState">
<Value name="1">
<Value>Name</Value>
</Value>
</Data>
<State>ClientTypeState</State>
<StreamLocation>^Shop.ClientTypeS</StreamLocation>
<Type>%Storage.Serial</Type>
}

}
