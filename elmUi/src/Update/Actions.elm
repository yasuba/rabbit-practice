module Update.Actions exposing (..)

import Model exposing (..)
import Msg exposing (..)
import List.Extra
import Guards exposing (..)

yospaceAdReplacementEnabled : Bool -> Model -> (Model, Cmd Msg)
yospaceAdReplacementEnabled boolean model =
    let
      oldEntry = model.newAdreplacementEntry
      newEntry =
        {oldEntry | isAdReplacementEnabled = boolean}
    in
    ({ model | newAdreplacementEntry = newEntry }, Cmd.none )


channel : String -> Model -> (Model, Cmd Msg)
channel name model =
    let
      oldEntry = model.newAdreplacementEntry
      newEntry =
        {oldEntry | adReplacementChannels =
             String.isEmpty oldEntry.adReplacementChannels => name
          |= String.contains name oldEntry.adReplacementChannels => deleteChannel oldEntry.adReplacementChannels name
          |= oldEntry.adReplacementChannels ++ ", " ++ name
        }
    in
    ( { model | newAdreplacementEntry = newEntry }, Cmd.none )


deleteChannel: String -> String -> String
deleteChannel oldString newString =
    let
      channelList = String.split ", " oldString
    in
      String.join ", " (List.Extra.filterNot (String.contains newString) channelList)


tokenRequired : Bool -> Model -> (Model, Cmd Msg)
tokenRequired boolean model =
    let
      oldEntry = model.newAdreplacementEntry
      newEntry =
        {oldEntry | isTokenRequired = boolean}
    in
    ( { model | newAdreplacementEntry = newEntry }, Cmd.none )


newProduct : String -> Model -> (Model, Cmd Msg)
newProduct newProduct model =
    let
      oldEntry = model.newAdreplacementEntry
      newEntry =
        {oldEntry | productName = newProduct}
    in
    ( { model | newAdreplacementEntry = newEntry }, Cmd.none )


fetchAdreplacementSettings : Model -> List AdReplacementEntry -> Model
fetchAdreplacementSettings model entry =
    { model | oldAdreplacementEntry = Maybe.withDefault model.oldAdreplacementEntry (List.head (List.reverse entry)) }


updateAdreplacementSettings : Model -> Model
updateAdreplacementSettings model =
    { model | oldAdreplacementEntry = model.newAdreplacementEntry }
