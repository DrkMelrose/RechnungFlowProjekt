import React, { useState } from "react";
import { Building2, FileText, Palette, Save } from "lucide-react";

export default function Settings() {
    const [theme, setTheme] = useState("Light");

    return (
        <div className="space-y-6">
            {/* Header */}
            <div>
                <h1 className="text-2xl font-semibold text-slate-900">Settings</h1>
                <p className="text-slate-500">
                    Manage company information, invoice defaults and appearance.
                </p>
            </div>

            <div className="grid gap-6 xl:grid-cols-2">
                {/* Company Information */}
                <section className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm">
                    <SectionHeader
                        icon={<Building2 size={20} />}
                        title="Company Information"
                        description="These details will appear on invoices."
                    />

                    <div className="mt-6 space-y-4">
                        <Input label="Company Name" defaultValue="RechnungFlow Cleaning GmbH" />
                        <Input label="Email" defaultValue="info@rechnungflow.de" />
                        <Input label="Phone" defaultValue="+49 151 123456" />
                        <Input label="Street" defaultValue="Bonner Straße 15" />

                        <div className="grid gap-4 sm:grid-cols-2">
                            <Input label="Postal Code" defaultValue="53111" />
                            <Input label="City" defaultValue="Bonn" />
                        </div>

                        <Input label="Tax Number" defaultValue="205/1234/5678" />
                        <Input label="VAT ID" defaultValue="DE123456789" />

                        <SaveButton />
                    </div>
                </section>

                {/* Invoice Settings */}
                <section className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm">
                    <SectionHeader
                        icon={<FileText size={20} />}
                        title="Invoice Settings"
                        description="Configure default values for generated invoices."
                    />

                    <div className="mt-6 space-y-4">
                        <Input label="Invoice Prefix" defaultValue="INV" />
                        <Input label="Next Invoice Number" defaultValue="2026-014" />
                        <Input label="VAT Rate (%)" defaultValue="19" />
                        <Input label="Payment Terms" defaultValue="14 Days" />

                        <div>
                            <label className="block text-sm font-medium text-slate-700 mb-2">
                                Default Invoice Status
                            </label>

                            <select className="w-full px-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400">
                                <option>Open</option>
                                <option>Paid</option>
                                <option>Draft</option>
                            </select>
                        </div>

                        <SaveButton />
                    </div>
                </section>

                {/* Appearance */}
                <section className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm xl:col-span-2">
                    <SectionHeader
                        icon={<Palette size={20} />}
                        title="Appearance"
                        description="Choose how the application should look."
                    />

                    <div className="mt-6 grid gap-4 sm:grid-cols-2">
                        <button
                            onClick={() => setTheme("Light")}
                            className={`text-left border rounded-2xl p-5 transition ${
                                theme === "Light"
                                    ? "border-blue-500 bg-blue-50"
                                    : "border-slate-200 hover:bg-slate-50"
                            }`}
                        >
                            <p className="font-semibold text-slate-900">Light Mode</p>
                            <p className="text-sm text-slate-500 mt-1">
                                Clean and bright interface.
                            </p>
                        </button>

                        <button
                            onClick={() => setTheme("Dark")}
                            className={`text-left border rounded-2xl p-5 transition ${
                                theme === "Dark"
                                    ? "border-blue-500 bg-blue-50"
                                    : "border-slate-200 hover:bg-slate-50"
                            }`}
                        >
                            <p className="font-semibold text-slate-900">Dark Mode</p>
                            <p className="text-sm text-slate-500 mt-1">
                                Planned for a later version.
                            </p>
                        </button>
                    </div>

                    <div className="mt-6">
                        <SaveButton label="Save Preferences" />
                    </div>
                </section>
            </div>
        </div>
    );
}

function SectionHeader({ icon, title, description }) {
    return (
        <div className="flex items-start gap-3">
            <div className="w-10 h-10 rounded-xl bg-blue-50 text-blue-600 flex items-center justify-center shrink-0">
                {icon}
            </div>

            <div>
                <h2 className="text-lg font-semibold text-slate-900">{title}</h2>
                <p className="text-sm text-slate-500">{description}</p>
            </div>
        </div>
    );
}

function Input({ label, defaultValue }) {
    return (
        <div>
            <label className="block text-sm font-medium text-slate-700 mb-2">
                {label}
            </label>

            <input
                type="text"
                defaultValue={defaultValue}
                className="w-full px-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
            />
        </div>
    );
}

function SaveButton({ label = "Save Changes" }) {
    return (
        <button className="w-full sm:w-auto flex items-center justify-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition">
            <Save size={16} />
            {label}
        </button>
    );
}