# Path to the directory containing the files
$directory = "C:\Users\am28r\JavaProjects\mario_poker\src\resources\png-cards\"

# Get all files with .svg extension in the directory
$svgFiles = Get-ChildItem -Path $directory -Filter "*_of_*.png"

foreach ($file in $svgFiles) {
    # Get the current file name
    $currentName = $file.Name
    
    # if the first 3 character are ace then the prefix is 1
    # else if the first 4 characters are jack then the prefix is 11
    # else if the first 5 characters are queen then the prefix is 12
    # else if the first 4 characters are king then the prefix is 13
    if ($currentName.Substring(0,3) -eq "ace") {
        $prefix = "1"
    } elseif ($currentName.Substring(0,2) -eq "10") {
        $prefix = "10"
    } elseif ($currentName.Substring(0,4) -eq "jack") {
        $prefix = "11"
    } elseif ($currentName.Substring(0,5) -eq "queen") {
        $prefix = "12"
    } elseif ($currentName.Substring(0,4) -eq "king") {
        $prefix = "13"
    } else {
        $prefix = $currentName.Substring(0, 1)
    }

    $suit = $currentName.Split("_")[2]

    $newName = $prefix + $suit
    $newName = $newName.Replace("_", "").Replace("_", "").ToUpper()

    # Print original name and new name
    Write-Host "$currentName > $newName"

    # Rename the file
    $newPath = Join-Path -Path $directory -ChildPath $newName
    Rename-Item -Path $file.FullName -NewName $newName -Force
}
