/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ryblade.openbikebcn.Wearable;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;

/**
 * Base class for notification priority presets.
 */
public class PriorityPreset extends NamedPreset {
    public PriorityPreset(int nameResId) {
        super(nameResId);
    }

    /** Apply the priority to a notification builder */
    public void apply(NotificationCompat.Builder builder,
            NotificationCompat.WearableExtender wearableOptions) {
        builder.setPriority(Notification.PRIORITY_HIGH);
    }
}