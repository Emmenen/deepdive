//将list[1,2,3]转为list[{value:1,label:""},{value:3,label:""}]
export function optionsDataForm(list){
    console.log("list",list)
    let options = []
    for (let i = 0; i < list.length; i++) {
        options.push({value: list[i],label: list[i]})
    }
    return options
}
