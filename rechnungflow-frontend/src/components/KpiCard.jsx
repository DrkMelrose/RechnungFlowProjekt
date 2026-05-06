function KpiCard({ title, value, change, icon: Icon }) {
    return (
        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm hover:shadow-md transition">
            <div className="flex items-start justify-between">
                <div>
                    <p className="text-sm text-slate-500 mb-2">{title}</p>
                    <p className="text-2xl font-bold text-slate-900">{value}</p>
                    <p className="text-xs text-emerald-600 mt-2">{change}</p>
                </div>
                <div className="h-12 w-12 rounded-2xl bg-blue-50 flex items-center justify-center text-blue-600">
                    <Icon size={23} />
                </div>
            </div>
        </div>
    );
}

export default KpiCard;