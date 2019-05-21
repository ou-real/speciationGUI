(* ::Package:: *)

BeginPackage["graphDefinitionPackage`"]

graph::usage = "graphs the file that is input to the function";
minimumFinder::usage = "takes in a function of the form of our seed distributions, and ouputs the minimums and thus the niche dividing lines of that function/seed distribution";

Begin["`Private`"]

graph[fileName_String]:=( 

data=Import[fileName];
means=Drop[data[[1]],-1];
stDevs=Drop[data[[2]],-1];
data=Drop[data, 3];
p1=ListPlot[data];

n[mean_, stDev_, t_]=1/2*(PDF[NormalDistribution[mean,stDev],t]-.025);
seeds[t_]:=0;
seeds[t]=0;
For[i=1,i<=Length[means],i++,seeds[t]=seeds[t]+n[means[[i]],stDevs[[i]],t]];

Print[Plot[seeds[t],{t,0,20}]];
mins = Range[Length[means]-1];
temp[g_, start_] := FindRoot[g[t] == 0, {t, start + .1}];
For[i=1, i <= Length[means]-1, i++, mins[[i]] = t /. FindRoot[seeds[t] == 0,
	{t, means[[i]] + .1}]];
(*mins = minimumFinder[seeds, t, means];*)
Print[mins];

p3 = Plot[5,{x,0,1000},PlotRange->{{0,1000},{0,10}}, PlotStyle->Green];
For[i = 1, i <= Length[mins], i++, p3 = Show[p3, 
	Plot[mins[[i]],{x,0,1000},PlotRange->{{0,1000},{0,10}}, PlotStyle->Green]]];

p2=DensityPlot[seeds[t], {x, 0 ,1000},{t,0,10}, ColorFunction->ColorData[{"CherryTones",
"Reverse"}], ColorFunctionScaling->False, AspectRatio->Full];

(*Print[p1];
Print[p2];
Print[p3];*)

Return[Show[p2, p1, PlotLabel->Style[Last[FileNameSplit[fileName]], FontSize->9]]]
(*Export["C:\\Users\\Tanner\\Desktop\\data from speciation research\\mathematica graph testing\\test1.jpg", Show[p2, p1]];*)
)

minimunFinder[f_, t_, means_] := (
mins = Range[Length[means]-1];
For[i=1, i <= Length[means]-1, i++, mins[[i]] = t /. FindRoot[f[t] == 0,
	{t, means[[i]] + .1}]];
return mins;
)
End[]
EndPackage[]













