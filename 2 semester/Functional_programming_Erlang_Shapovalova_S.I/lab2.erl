 -module(lab2).
 -export([func1/1]).
 -export([func2/1]).

func1(X) when (1 < x) and (x < 2) ->
	X;
func1(X) when X > 2 ->
	math:pow((479001600 * X), 12).

func2(X) ->
	if
		(1 < x) and (x < 2) -> X;
		X>2 -> math:pow((479001600 * X), 12)
	end.

